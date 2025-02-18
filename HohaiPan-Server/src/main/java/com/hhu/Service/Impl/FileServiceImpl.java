package com.hhu.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.entity.File;
import com.hhu.enums.CategoryCodeType;
import com.hhu.mapper.FileMapper;
import com.hhu.result.PageBean;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.utils.HHUThreadLocalUtil;
import com.hhu.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public PageBean getFileList(FileListDTO fileListDTO) {
        String filePid = fileListDTO.getFilePid();
        String categoryCodeStr = fileListDTO.getCategoryCode();
        Integer categoryCode = CategoryCodeType.getTypeCode(categoryCodeStr);
        String fileNameFuzzy = fileListDTO.getFileNameFuzzy();

        Long userId = HHUThreadLocalUtil.getUserId();

        Page<File> page = lambdaQuery().eq(!StrUtil.isBlank(filePid),File::getFilePid,filePid)
                .eq(categoryCode != null,File::getFileCategory,categoryCode )
                .like(!StrUtil.isBlank(fileNameFuzzy),File::getFileName,fileNameFuzzy)
                .eq(File::getUserId,userId)
                .page(Page.of(fileListDTO.getPageNum(), fileListDTO.getPageSize()));

        List<File> list = page.getRecords();
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
