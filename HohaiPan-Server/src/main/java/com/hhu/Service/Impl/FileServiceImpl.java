package com.hhu.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.entity.File;
import com.hhu.mapper.FileMapper;
import com.hhu.result.PageBean;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hhu.enums.CategoryCodeType.getTypeCode;
import static com.hhu.enums.CategoryCodeType.getTypeName;

@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
    @Override
    public PageBean getFileList(FileListDTO fileListDTO) {
        //TODO分页查询 条件查询 分类为all返回的是文件夹
        String filePid = fileListDTO.getFilePid();
        String categoryCode = fileListDTO.getCategoryCode();
        String fileNameFuzzy = fileListDTO.getFileNameFuzzy();
        System.out.println(StrUtil.isBlank(filePid)+"9999");
        Page<File> page = lambdaQuery().eq(!StrUtil.isBlank(filePid),File::getFilePid,filePid)
                .eq(!StrUtil.isBlank(categoryCode),File::getFileCategory,categoryCode )
                .like(!StrUtil.isBlank(fileNameFuzzy),File::getFileName,fileNameFuzzy)
                .page(Page.of(fileListDTO.getPageNum(), fileListDTO.getPageSize()));
        List<File> list = page.getRecords();
        System.out.println("list:"+ list);
        List<FileVO> fileVOList = list.stream().map(file -> FileVO.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .fileCategory(file.getFileCategory())
                .fileType(file.getFileType())
                .createTime(file.getCreateTime())
                .build()).toList();
        return new PageBean(page.getTotal(), fileVOList);
    }
}
