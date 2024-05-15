package kusitms.jangkku.global.exception;

import kusitms.jangkku.global.common.code.BaseErrorCode;
import kusitms.jangkku.global.common.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorResult implements BaseErrorCode {
    IMAGE_SIZE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "413", "이미지 크기가 너무 큽니다."),
    CONVERSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "500", "File로의 전환에 실패했습니다."),
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "500", "S3 파일 업로드에 실패했습니다."),
    S3_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "500", "S3 파일 삭제에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}