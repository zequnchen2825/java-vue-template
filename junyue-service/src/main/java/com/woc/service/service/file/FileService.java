package com.woc.service.service.file;

import com.woc.common.entity.TUser;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file,Integer type) throws Exception;

    String uploadFile(TUser tUser, MultipartFile file) throws Exception;
}
