package kusitms.jangkku.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kusitms.jangkku.global.exception.S3ErrorResult;
import kusitms.jangkku.global.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Util {
    @Value("${cloud.aws.credentials.bucket}")
    private String BUCKET;
    @Value("${cloud.aws.s3.endpoint}")
    private String END_POINT;

    private final AmazonS3 amazonS3Client;

    // S3에 파일을 업로드하는 메서드
    public String uploadProfileImg(MultipartFile file) {
        // 이미지 크기 확인
        if (file.getSize() > 52428800) {
            throw new S3Exception(S3ErrorResult.IMAGE_SIZE_TOO_LARGE); // 이미지 크기가 너무 큰 경우
        }

        try {
            String fileName = file.getOriginalFilename();
            String fileUrl= END_POINT + "/" + BUCKET + "/" + fileName;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(new PutObjectRequest(BUCKET, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return fileUrl;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new S3Exception(S3ErrorResult.S3_UPLOAD_FAILED);
        }
    }

    // S3에서 파일을 삭제하는 메서드
    public void deleteS3(String s3Url) throws S3Exception {
        try {
            String fileName = extractKeyFromS3Url(s3Url);
            amazonS3Client.deleteObject(BUCKET, fileName);
            log.info("S3 파일이 삭제되었습니다.");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new S3Exception(S3ErrorResult.S3_DELETE_FAILED);
        }
    }

    // S3 URL에서 filename 추출
    public String extractKeyFromS3Url(String s3Url) {
        String[] urlParts = s3Url.split("/");
        String fileName = urlParts[urlParts.length - 1];

        return fileName;
    }
}