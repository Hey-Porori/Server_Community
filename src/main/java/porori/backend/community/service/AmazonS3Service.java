package porori.backend.community.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import porori.backend.community.dto.PostResDto;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AmazonS3Service {
    private final AmazonS3 amazonS3;
    private final UserService userService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public PostResDto.PreSignedUrlRes getPreSignedUrl(String token){
        //토큰 유효 확인
        userService.sendTestJwtRequest(token);


        String uuid = UUID.randomUUID().toString();
        String objectKey = "communities/"+uuid;

        GeneratePresignedUrlRequest request = generatePresignedUrlRequest(bucket, objectKey);
        PostResDto.PreSignedUrlRes urlRes = PostResDto.PreSignedUrlRes.builder()
                .url(amazonS3.generatePresignedUrl(request).toString())
                .build();
        return urlRes;
    }

    private GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucket, String imageName){

        //만료 시간 설정
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 5; // 5분
        expiration.setTime(expTimeMillis);

        //Pre-Signed Url request 생성
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, imageName)
                                                    .withMethod(HttpMethod.PUT)
                                                    .withExpiration(expiration);
        //request 파라미터 추가
        request.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        return request;
    }
}
