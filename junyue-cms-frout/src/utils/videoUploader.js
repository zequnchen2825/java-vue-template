import "@/utils/aliyun-upload-sdk-1.3.1/aliyun-upload-sdk1.3.1.min.js";
import { getVideoAuth, refreshVideoAuth } from "@/api/sys";
import OSS from "ali-oss";
export async function uploadVideo(
  file,
  accessKeyId,
  accessKeySecret,
  securityToken,
  extraMethods,
  subject
) {
  console.log('subject----', subject)
  return new Promise(async (resolve, reject) => {
    var vedioAuth = await getVideoAuth(file.name, file.size, subject);
    const { requestId, uploadAddress, uploadAuth, videoId } = vedioAuth.data;
    const uploader = new AliyunUpload.Vod({
      // 开始上传
      onUploadstarted: function(uploadInfo) {
        console.log(
          "onUploadStarted:" +
            uploadInfo +
            ", endpoint:" +
            uploadInfo.endpoint +
            ", bucket:" +
            uploadInfo.bucket +
            ", object:" +
            uploadInfo.object
        );
        //上传方式1, 需要根据uploadInfo.videoId是否有值，调用点播的不同接口获取uploadauth和uploadAddress，如果videoId有值，调用刷新视频上传凭证接口，否则调用创建视频上传凭证接口
        uploader.setUploadAuthAndAddress(
          uploadInfo,
          uploadAuth,
          uploadAddress,
          videoId
        );
        //上传方式2
        // uploader.setSTSToken(uploadInfo, accessKeyId, accessKeySecret,secretToken);
      },
      // 文件上传成功
      onUploadSucceed: function(uploadInfo) {
        resolve({
          uploadInfo: uploadInfo,
          fileName: file.name,
          fileSize: file.size
        });
      },
      // 文件上传失败
      onUploadFailed: function(uploadInfo, code, message) {
        console.log(
          "onUploadFailed: file:" +
            uploadInfo.file +
            ",code:" +
            code +
            ", message:" +
            message
        );
      },
      // 文件上传进度，单位：字节
      onUploadProgress: function(uploadInfo, totalSize, loadedPercent) {
        // console.log(
        //   "onUploadProgress:file:" +
        //     uploadInfo.file.name +
        //     ", fileSize:" +
        //     totalSize +
        //     ", percent:" +
        //     Math.ceil(loadedPercent * 100) +
        //     "%"
        // );
        let e = new Event("process");
        e.percent = Math.ceil(loadedPercent * 100);
        try {
          window.uploadProcess.dispatchEvent(e);
        } catch (e) {

        }
        // resolve({
        //   loadedPercent
        // });
        if(extraMethods && extraMethods.handleProgress) {
          try {
            extraMethods.handleProgress(e)
          } catch (e) {

          }
        }
      },
      // 上传凭证超时
      onUploadTokenExpired: async function(uploadInfo) {
        console.log("onUploadTokenExpired");

        var refresh = await refreshVideoAuth(videoId);
        const { requestId, uploadAuth } = refresh.data;
        //上传方式1  实现时，根据uploadInfo.videoId调用刷新视频上传凭证接口重新获取UploadAuth
        uploader.resumeUploadWithAuth(uploadAuth);
        // 上传方式2 实现时，从新获取STS临时账号用于恢复上传
        // uploader.resumeUploadWithSTSToken(accessKeyId, accessKeySecret, secretToken, expireTime);
      },
      //全部文件上传结束
      onUploadEnd: function(uploadInfo) {
        console.log("onUploadEnd: uploaded all the files");
      }
    });

    console.log(file);
    uploader.init(accessKeyId, accessKeySecret);
    // uploader.resumeUploadWithToken(
    //   accessKeyId,
    //   accessKeySecret,
    //   securityToken,
    //   0
    // );
    // const userData =
    //   '{"Vod":{"UserData":"{"IsShowWaterMark":"false","Priority":"7"}"}}';
    uploader.addFile(file, null, null, null);
    uploader.startUpload();
    return uploader;
  });
}
