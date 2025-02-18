package com.hhu.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.entity.File;
import com.hhu.entity.Share;
import com.hhu.mapper.ShareMapper;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.service.IShareService;
import com.hhu.utils.HHUPartitionedBloomFilter;
import com.hhu.utils.HHUThreadLocalUtil;
import com.hhu.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements IShareService {

    @Autowired
    private ShareMapper shareMapper;

    // 定义允许的字符集（字母和数字）
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // 分享码的长度
    private static final int SHARE_CODE_LENGTH = 6;

    @Autowired
    private HHUPartitionedBloomFilter hhuPartitionedBloomFilter;
    @Autowired
    private IFileService fileService;

    @Override
    public Result getShareFile(String shareCode) {
        boolean exists = isShareCodeExists(shareCode);
        if (!exists) {
            return Result.error("分享码不存在");
        }

        File file = shareMapper.getFileByShareCode(shareCode);
        log.info("获得分享文件:{}", file);
        if(file == null){
            return Result.error("分享文件不存在或已删除");
        }
        //转换为VO
        FileVO fileVO = BeanUtil.copyProperties(file, FileVO.class);
        return Result.success(fileVO);
    }

    @Override
    public Result generateShareCode(String fileId) {
        File file = fileService.getById(fileId);
        if (file == null) {
            return Result.error("文件不存在");
        }
        String shareCode = generateShareCode(0);
        if (shareCode == null) {
            return Result.error("生成分享码失败");
        }
        //构建 share 对象
        Long userId = HHUThreadLocalUtil.getUserId();
        Share share = new Share().builder()
                .shareCode(shareCode)
                .fileId(fileId)
                .userId(userId)
                .shareTime(LocalDateTime.now())
                .expireTime(LocalDate.now().plusDays(7))
                .build();
        log.info("生成分享码:{}", share);
        boolean save = save(share);

        if (!save) {
            return Result.error("生成分享码失败");
        }

        hhuPartitionedBloomFilter.partitionedAdd(shareCode, "shareCode");
        log.info("分享码:{} 已存入布隆过滤器", shareCode);
        return Result.success(shareCode);
    }

    private boolean isShareCodeExists(String shareCode) {
        // 检查分享码是否存在于布隆过滤器中
        boolean contains = hhuPartitionedBloomFilter.partitionedContains(shareCode, "shareCode");
        if (!contains) {
            return false;
        }
        // 继续查数据库判断是否存在
        Share one = lambdaQuery()
                .eq(Share::getShareCode, shareCode)
                .ge(Share::getExpireTime, LocalDate.now())
                .one();
        if (one == null){
            return false;
        }
        //更新次数
        lambdaUpdate()
                .eq(Share::getShareCode, shareCode)
                .set(Share::getUseCount, one.getUseCount() + 1)
                .update();
        return true;
    }

    private String generateShareCode(Integer times) {
        //递归调用生成
        if (times >= 5) {
            return null;
        }
        Random random = new Random();
        StringBuilder shareCode = new StringBuilder(SHARE_CODE_LENGTH);

        // 随机选择字符集中的字符，生成分享码
        for (int i = 0; i < SHARE_CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            shareCode.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        //判断是否重复
        boolean exists = isShareCodeExists(shareCode.toString());
        if (exists) {
            //重新生成
            return generateShareCode(times + 1);
        }

        log.info("生成分享码:{} 递归次数:{}", shareCode, times + 1);
        return shareCode.toString();
    }
}
