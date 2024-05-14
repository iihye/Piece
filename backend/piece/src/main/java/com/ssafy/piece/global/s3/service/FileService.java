package com.ssafy.piece.global.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

import com.ssafy.piece.domain.users.service.UsersService;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${amazon.aws.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Autowired
    private UsersService usersService;

    /**
     * presigned url 발급
     *
     * @param prefix   버킷 디렉토리명로드할 파일명
     * @param fileName 클라이언트가 업
     * @return presigned url
     */
    public String getPreSignedUrl(String prefix, String fileName) {
        if (!prefix.isEmpty()) {
            fileName = createPath(prefix, fileName);
        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, fileName);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
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
     * presigned url 유효 기간 설정
     *
     * @return 유효기간
     */
    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10 * 6;  // 한시간
        expiration.setTime(expTimeMillis);
        return expiration;
    }


    /**
     * 파일 공유 ID 생성
     *
     * @return UUID
     */
    private String createFileId() {
        // 파일 고유 UUID
        return UUID.randomUUID().toString();
    }


    /**
     * 파일의 전체 경로 생성
     *
     * @param prefix   디렉토리 경로
     * @param fileName 파일의 전체 경로
     * @return 파일의 전체 경로
     */
    private String createPath(String prefix, String fileName) {
        String fileId = createFileId();
        String AWSFilePath = String.format("%s/%s%s", prefix, fileId, fileName);
//        return String.format("%s/%s", prefix, fileId + fileName);
//        return saveAWSFilePath(AWSFilePath);
        saveAWSFilePath(prefix, AWSFilePath);
        return AWSFilePath;
    }


    /**
     * users 테이블에 profileImage 저장
     * @param prefix  userId
     * @param AWSFilePath
     */
    public void saveAWSFilePath(String prefix, String AWSFilePath) {
        String S3Path = "https://s3.ap-southeast-2.amazonaws.com/piecemaker.kr/" + AWSFilePath;
        log.info("S3Path is {}", S3Path);
        usersService.updateUserProfileImage(prefix, S3Path);
    }
}