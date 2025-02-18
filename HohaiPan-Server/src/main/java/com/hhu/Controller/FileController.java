package com.hhu.controller;

import cn.hutool.core.lang.UUID;
import com.hhu.dto.FileDTO.FileListDTO;
import com.hhu.result.PageBean;
import com.hhu.result.Result;
import com.hhu.service.IFileService;
import com.hhu.utils.HHUAliOssUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${HohaiPan.StoragePath.filePath}")
    private String filePath;

    @Value("${HohaiPan.StoragePath.avatarPath}")
    private String avatarPath;

    @Autowired
    private IFileService fileService;
    @Autowired
    private HHUAliOssUtils hhuAliOssUtils;

    @GetMapping("/list")
    public Result list(FileListDTO fileListDTO) {
        log.info("fileListDTO:{}",fileListDTO);
        PageBean fileList = fileService.getFileList(fileListDTO);
        log.info("fileList:{}",fileList);
        return Result.success(fileList);
    }

    @PostMapping("/avatar/upload")
    public Result avatarUpload(MultipartFile file) throws Exception {
        UUID uuid = UUID.randomUUID();
        //生成 UUID + 文件扩展名 的文件存储格式
        String originalFilename = file.getOriginalFilename();
        String[] parts = originalFilename.split("\\.");
        String extension = parts.length > 1 ? parts[parts.length - 1] : "";
        String FileName = uuid + "." + extension;

        //阿里Oss存储方式
        String fileUrl = hhuAliOssUtils.uploadFile(FileName,file.getInputStream());
        return Result.success(fileUrl);

        //本体存储方式
//        //创建目录
//        File directory = new File(avatarPath);
//        // 检查目录是否存在，不存在则创建
//        if (!directory.exists()) {
//            boolean created = directory.mkdirs(); // 创建多级目录
//            if (created) {
//                log.info("成功创建目录: {}", avatarPath); // 日志记录
//            } else {
//                log.error("目录创建失败: {}", avatarPath); // 日志记录
//                return Result.error("目录创建失败");
//            }
//        }
//
//        file.transferTo(new File(avatarPath + FileName));
//        return Result.success(FileName);
    }

    //本地存储的图片回显方式
//    @GetMapping("/avatar/{avatarName}")
//    public void getAvatar(@PathVariable String avatarName, HttpServletResponse response) throws IOException {
//        // 图片文件路径
//        File file = new File(avatarPath + avatarName);
//
//        // 如果文件不存在，返回 404
//        if (!file.exists()) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
//
//        // 设置响应头
//        response.setContentType("image/jpeg"); // 根据图片类型设置
//        FileInputStream fis = new FileInputStream(file);
//        OutputStream os = response.getOutputStream();
//
//        // 流式输出图片
//        byte[] buffer = new byte[1024];
//        int len;
//        while ((len = fis.read(buffer)) != -1) {
//            os.write(buffer, 0, len);
//        }
//        fis.close();
//        os.flush();
//    }
}
