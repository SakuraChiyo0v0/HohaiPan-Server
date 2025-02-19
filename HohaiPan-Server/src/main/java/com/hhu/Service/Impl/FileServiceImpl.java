package com.hhu.service.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.entity.File;
import com.hhu.enums.CategoryCodeType;
import com.hhu.enums.FileType;
import com.hhu.mapper.FileMapper;
import com.hhu.result.PageBean;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.utils.HHUThreadLocalUtil;
import com.hhu.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Override
    public Result rename(String fileId, String newName) {
        File file = getById(fileId);
        if (file == null) {
            return Result.error("文件不存在");
        }
        boolean update = lambdaUpdate().eq(File::getFileId, fileId)
                .set(File::getFileName, newName)
                .set(File::getUpdateTime, LocalDateTime.now())
                .update();
        if (!update) {
            return Result.error("重命名失败");
        }
        return Result.success();
    }

    @Override
    public Result deleteFile(String[] fileIds) {
        log.info("fileIds:"+ Arrays.toString(fileIds));
        for (int i = 0; i < fileIds.length; i++) {
            System.out.println(fileIds[i]);
        }
        return Result.success();
    }

    @Override
    public Result createFolder(String filePid, String fileName) {
        List<File> folderList = getFolderListByPid(filePid);
        if(folderList==null){
            return Result.error("文件夹不存在或状态不合法");
        }
        long count = folderList.stream().filter(file -> file.getFileName().equals(fileName)).count();
        if(count>0){
            return Result.error("文件夹不能重名");
        }
        File file = File.builder()
                .userId(HHUThreadLocalUtil.getUserId())
                .fileId(IdUtil.randomUUID())
                .filePid(filePid)
                .fileSize(0L)
                .fileName(fileName)
                .fileType((byte) 0)
                .status((byte) 2)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        boolean isSaved = save(file);
        if(!isSaved){
            return Result.error("创建文件夹失败");
        }

        log.error("test:null");
        for (File file1 : getFolderListByPid(null)) {
            System.out.println(file1.getFileName());
        }
        log.error("test:0");
        for (File file1 : getFolderListByPid("0")) {
            System.out.println(file1.getFileName());
        }
        log.error("test:0");
        System.out.println(getFileType("1892095118187925505"));
        System.out.println(getFileType("2cJd8bpEzU"));
        return Result.success();
    }

    /*
    判断fileId是否为文件夹
     */
    private FileType getFileType(String fileId){
        //为0是默认为主目录 可视为文件夹
        if(fileId.equals("0")){
            return FileType.FOLDER;
        }
        File file = getById(fileId);
        if(file==null){
            return FileType.NON_EXISTENT;
        }
        return file.getFileType()==0?FileType.FOLDER:FileType.FILE;
    }
    /*
    得到用户的所有文件
     */
    private List<File> getFileListByPid(String filePid){
        if(filePid!=null){
            FileType fileType = getFileType(filePid);
            if(fileType!=FileType.FOLDER){
                return null;
            }
        }
        Long userId = HHUThreadLocalUtil.getUserId();
        return lambdaQuery().eq(filePid!=null,File::getFilePid,filePid)
                .eq(File::getUserId,userId)
                .list();
    }

    /*
    * 得到用户的所有文件夹(Pid为null时)
     */
    private List<File> getFolderListByPid(String filePid){
        if(filePid!=null){
            FileType fileType = getFileType(filePid);
            if(fileType!=FileType.FOLDER){
                return null;
            }
        }
        Long userId = HHUThreadLocalUtil.getUserId();
        return lambdaQuery().eq(filePid!=null,File::getFilePid,filePid)
                .eq(File::getUserId,userId)
                .eq(File::getFileType,0)
                .list();
    }

}
