package com.example.order_service.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class OSSUtil {
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private String accessKeyId = "LTAI5t62TN5Btgq6jQ4o43jo";
    private String accessKeySecret = "JOxCT94Zf9Gsg90KGuEPg5sObB1BC9";
    // 填写Bucket名称，例如examplebucket。
    private String bucketName = "1217078825";
    //目录
    private String baseUrl = "productPicture/";
    //创建公用OSS客户端
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    public String uploadNewPPictiure(MultipartFile file, String sourceName) throws IOException {
        //获取图片文件的字节流
        InputStream inputStream = file.getInputStream();
        String desUrl = baseUrl + sourceName;

        //修改图片保存格式，使其可以直接预览
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpg");

        ossClient.putObject(bucketName, desUrl, inputStream, objectMetadata);

        String accessSourceUrl = bucketName + "." + endpoint + "/" + baseUrl + sourceName;
        return accessSourceUrl;
    }

}
