package kusitms.jangkku.domain.persona.constant;

import kusitms.jangkku.global.common.code.BaseCode;
import kusitms.jangkku.global.common.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PersonaSuccessStatus implements BaseCode {
    // 정의하기 페르소나
    CREATED_DEFINE_PERSONA(HttpStatus.CREATED, "201", "정의하기 페르소나 생성에 성공했습니다."),
    GET_DEFINE_PERSONA(HttpStatus.OK, "200", "정의하기 페르소나 조회에 성공했습니다."),
    // 설계하기 페르소나
    CREATED_DESIGN_PERSONA(HttpStatus.CREATED, "201", "설계하기 페르소나 생성에 성공했습니다."),
    GET_DESIGN_PERSONA(HttpStatus.CREATED, "200", "설계하기 페르소나 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .isSuccess(true)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .isSuccess(true)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}