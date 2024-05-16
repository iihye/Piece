package com.ssafy.piece.global.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${amazon.aws.bucket}")
    private String bucket;

    @Value("${amazon.aws.prefixAddress}")
    private String prefixAddress;

    private final AmazonS3 amazonS3;

    /**
     * presigned url 발급
     *
     * @param bucketFileName   버킷 디렉토리명 = userId
     * @param fileName 클라이언트가 업로드할 파일명
     * @return presigned url
     */
    public ArrayList<String> getPreSignedUrl(String bucketFileName, String fileName) {
        if (!bucketFileName.isEmpty()) {
            fileName = createPath(bucketFileName, fileName);
        }

        log.info("fileName is {}", fileName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, fileName);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        ArrayList<String> response = new ArrayList<>();
        response.add(url.toString());
        response.add(S3FilePath(fileName));
//        return url.toString();
        return response;
    }


    /**
     * 파일 업로드용 (PUT) presigned url 생성
     *
     * @param bucket   버킷명
     * @param fileName S3 업로드용 파일명
     * @return presigned url
     */
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
            Headers.S3_CANNED_ACL,
            CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }


    /**
     * presigned url 유효시간 설정
     * @return 유효시간 10분
     */
    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;  // 10분
        expiration.setTime(expTimeMillis);
        return expiration;
    }


    /**
     * 파일의 전체 경로 생성
     *
     * @param bucketFileName   버킷 디렉토리명
     * @param fileName  파일의 전체 경로
     * @return 파일의 전체 경로
     */
    private String createPath(String bucketFileName, String fileName) {
        String fileId = createFileId();
        return String.format("%s/%s%s", bucketFileName, fileId, fileName);
    }


    /**
     * 파일 공유 ID 생성
     * @return UUID
     */
    private String createFileId() {
        // 파일 고유 UUID
        return UUID.randomUUID().toString();
    }


    /**
     * 파일의 전체 경로 생성
     *
     * @param fileName
     * @return  파일의 전체 경로
     */
    public String S3FilePath(String fileName) {
        String S3Path = "https://" + prefixAddress + fileName;
        log.info("S3Path is {}", S3Path);
        return S3Path;
    }


}