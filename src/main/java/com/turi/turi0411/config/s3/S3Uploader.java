package com.turi.turi0411.config.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String uploadFile(MultipartFile multipartFile) {

        String newFileName = UUID.randomUUID() + multipartFile.getName();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, newFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            log.debug("아마존 이미지 업로드 실패");
        }

        return amazonS3Client.getUrl(bucket, newFileName).toString();
    }

    public void deleteFile(String fileName) {
        log.info("파일 삭제. 파일 이름: " + fileName);
        try {
            amazonS3Client.deleteObject(bucket, (fileName).replace(File.separatorChar, '/'));
        } catch(AmazonServiceException ex) {
            System.err.println(ex.getErrorMessage());
        }
    }


}
