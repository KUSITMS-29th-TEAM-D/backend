package kusitms.jangkku.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class S3Exception extends RuntimeException {
    private final S3ErrorResult s3ErrorResult;

    @Override
    public String getMessage() {
        return s3ErrorResult.getMessage();
    }
}