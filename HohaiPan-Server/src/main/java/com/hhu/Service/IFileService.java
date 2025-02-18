package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.entity.File;
import com.hhu.result.PageBean;

public interface IFileService extends IService<File> {
    PageBean getFileList(FileListDTO fileListDTO);

}
