package com.hhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhu.entity.Share;
import com.hhu.result.Result;

public interface IShareService extends IService<Share> {
    Result getShareFile(String shareCode);

    Result generateShareCode(String fileId);
}
