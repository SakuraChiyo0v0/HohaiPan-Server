package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.entity.File;
import com.hhu.result.PageBean;
import com.hhu.result.Result;

public interface IFileService extends IService<File> {
    PageBean getFileList(FileListDTO fileListDTO);

    Result rename(String fileId, String newName);

    Result deleteFile(String[] fileIds);

    Result createFolder(String filePid, String fileName);
}
