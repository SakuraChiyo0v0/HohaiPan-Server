package com.hhu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhu.dto.FileDTO.FileShareDTO;
import com.hhu.entity.File;
import com.hhu.entity.Share;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShareMapper extends BaseMapper<Share> {

    FileShareDTO getFileByShareCode(String shareCode);
}
