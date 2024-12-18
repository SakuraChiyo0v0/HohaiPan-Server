package com.hhu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.entity.File;
import com.hhu.mapper.FileMapper;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hhu.enums.CategoryCodeType.getTypeCode;

@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
    @Override
    public Result getFileList(FileListDTO fileListDTO) {
        //TODO分页查询 条件查询 分类为all返回的是文件夹
        List<File> list = lambdaQuery().eq(File::getUserId, fileListDTO.getFilePid())
                .eq(File::getFileCategory, getTypeCode(fileListDTO.getCategoryCode()))
                .like(File::getFileName, fileListDTO.getFileNameFuzzy())
                .list();
        List<FileVO> fileVOList = list.stream().map(file -> FileVO.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .fileCategory(file.getFileCategory())
                .fileType(file.getFileType())
                .createTime(file.getCreateTime())
                .build()).toList();
        return Result.success(fileVOList);
    }
}
