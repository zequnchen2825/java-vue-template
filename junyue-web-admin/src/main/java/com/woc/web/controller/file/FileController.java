package com.woc.web.controller.file;


import com.woc.common.common.Contants;
import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.TUser;
import com.woc.service.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @ResponseBody
    @RequestMapping("/uploadFile")
    public RespResult uploadFile(MultipartFile file,@RequestAttribute(Contants.USER) TUser tUser) throws Exception{
        return new RespResult(fileService.uploadFile(tUser,file));
    }
}
