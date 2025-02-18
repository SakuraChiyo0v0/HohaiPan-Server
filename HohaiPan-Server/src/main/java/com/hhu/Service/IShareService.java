package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.entity.File;
import com.hhu.result.Result;

public interface IShareService extends IService<File> {
    Result getShareFile(String shareCode);

    Result generateShareCode(String fileId);
}
