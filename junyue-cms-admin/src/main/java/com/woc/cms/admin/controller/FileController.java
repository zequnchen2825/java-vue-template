package com.woc.cms.admin.controller;

import com.woc.common.common.bean.RespResult;
import com.woc.service.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping("/uploadFile")
    public RespResult uploadFile(MultipartFile file,Integer type) throws Exception{

        return new RespResult(fileService.uploadFile(file,type));

    }
}
