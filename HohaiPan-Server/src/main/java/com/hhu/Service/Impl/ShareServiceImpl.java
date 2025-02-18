package com.hhu.service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.entity.File;
import com.hhu.mapper.FileMapper;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.service.IShareService;
import com.hhu.utils.HHUPartitionedBloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class ShareServiceImpl extends ServiceImpl<FileMapper, File> implements IShareService {

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
        if(!exists){
            return Result.error("分享码不存在");
        }
        //TODO获得关联的文件信息并返回
        return Result.success(shareCode);
    }

    @Override
    public Result generateShareCode(String fileId) {
        File file = fileService.getById(fileId);
        if(file == null){
            return Result.error("文件不存在");
        }
        String shareCode = generateShareCode(0);
        if (shareCode == null){
            return Result.error("生成分享码失败");
        }
        //TODO 分享码关联文件 并 存入布隆过滤器中
        return Result.success(shareCode);
    }

    private boolean isShareCodeExists(String shareCode) {
        // 检查分享码是否存在于布隆过滤器中
        boolean contains = hhuPartitionedBloomFilter.partitionedContains(shareCode, "shareCode");
        if (!contains){
            return false;
        }
        // 继续查数据库判断是否存在
        //TODO检查分享嘛是否真的存在
        boolean exists = false;
        return exists;
    }

    private String generateShareCode(Integer times) {
        //递归调用生成
        if(times >= 5){
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
        if (exists){
            //重新生成
            return generateShareCode(times+1);
        }

        log.info("生成分享码:{} 递归次数:{}",shareCode,times+1);
        return shareCode.toString();
    }
}
