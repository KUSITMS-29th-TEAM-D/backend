package kusitms.jangkku.domain.persona.exception;

import kusitms.jangkku.global.common.code.BaseErrorCode;
import kusitms.jangkku.global.common.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PersonaErrorResult implements BaseErrorCode {
    NOT_FOUND_PERSONA_TYPE(HttpStatus.NOT_FOUND, "404", "존재하지 않는 페르소나 타입입니다."),
    NOT_FOUND_DEFINE_PERSONA(HttpStatus.NOT_FOUND, "404", "정의하기 페르소나가 존재하지 않습니다."),
    IS_ALREADY_COMPLETED(HttpStatus.CONFLICT, "409", "이미 답변이 종료된 카테고리입니다."),
    NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, "404", "존재하지 않는 질문입니다."),
    NOT_FOUND_CHATTING(HttpStatus.NOT_FOUND, "404", "존재하지 않는 채팅입니다."),
    NOT_FOUND_ANY_KEYWORDS(HttpStatus.NOT_FOUND, "404", "테스트를 진행하지 않아 키워드가 존재하지 않습니다."),
    NOT_FOUND_KEYWORDS(HttpStatus.NOT_FOUND, "404", "대화가 완료되지 않아 키워드가 존재하지 않습니다.");

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