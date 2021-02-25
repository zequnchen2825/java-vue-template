package com.woc.service.service.file.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.woc.common.common.RespContants;
import com.woc.common.common.config.OssConfig;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.shiro.ShiroManager;
import com.woc.common.common.utils.DateUtils;
import com.woc.common.common.utils.FileUilts;
import com.woc.common.entity.TUser;
import com.woc.common.entity.WocPlatform;
import com.woc.service.service.file.FileService;
import com.woc.service.service.login.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private PlatformService platformService;

    private static final String IMAGE = "image";
    private static final String AUDIO = "audio";
    private static final String VIDEO = "video";

    private static final Long SIZE_MB = 1048576L;

    @Override
    public String uploadFile(MultipartFile file,Integer type) throws Exception {
        WocPlatform platform = ShiroManager.getPlatform();

        return updateFile(file,platform);
    }

    @Override
    public String uploadFile(TUser tUser, MultipartFile file) throws Exception {
        WocPlatform wocPlatform = platformService.findById(tUser.getPlatformId());
        return updateFile(file,wocPlatform);
    }

    public String updateFile(MultipartFile file,WocPlatform platform) throws Exception{
        int pos = file.getOriginalFilename().lastIndexOf(".");
        //判断点的位置
        if (pos<0){
            return null;
        }

        StringBuffer buf  = new StringBuffer();
        //获得文件的扩展名,png,jpg等
        String fileExt = file.getOriginalFilename().substring(pos+1).toLowerCase();

        String fileType = FileUilts.fileType(fileExt);
        buf.append(fileType);

        long size = file.getSize();
        if(IMAGE.equals(fileType)){
            if(size > 2 * SIZE_MB){
                throw new BusinessException(RespContants.SIZE_IS_TOO_LAGER,",2M");
            }
        }else if(AUDIO.equals(fileType)){
            if(size > 5 *SIZE_MB){
                throw new BusinessException(RespContants.SIZE_IS_TOO_LAGER,",5M");
            }
        }else if(VIDEO.equals(fileType)){
            if(size > 10 *SIZE_MB){
                throw new BusinessException(RespContants.SIZE_IS_TOO_LAGER,",10M");
            }
        }else{
            throw new BusinessException(RespContants.FILE_TYPE_ERROE);
        }
        String dateLong = DateUtils.getDateLong();
        if(buf.length() >0){
            buf.append("/");
        }
        buf.append(dateLong);
        String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        buf.append("/");
        buf.append(fileName);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(platform.getBucket(), buf.toString(), file.getInputStream());

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
        String baseFileUrl = String.format(ossConfig.getBaseFileUrl(), platform.getBucket());
        return baseFileUrl+"/"+buf.toString();
    }
}
