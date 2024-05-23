package kusitms.jangkku.domain.user.constant;

import kusitms.jangkku.global.common.code.BaseCode;
import kusitms.jangkku.global.common.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessStatus implements BaseCode {
    // 유저
    SUCCESS_REGISTER_USER(HttpStatus.CREATED, "201", "유저 등록에 성공했습니다."),
    IS_NICKNAME_POSSIBLE(HttpStatus.OK, "200", "사용할 수 있는 닉네임입니다."),
    SUCCESS_UPLOAD_PROFILE_IMG(HttpStatus.OK, "200", "프로필 사진 수정에 성공했습니다."),
    SUCCESS_GET_USER_INFOS(HttpStatus.OK, "200", "유저 정보 조회에 성공했습니다."),
    SUCCESS_EDIT_USER_INFOS(HttpStatus.OK, "200", "유저 정보 수정에 성공했습니다."),
    SUCCESS_LOGOUT(HttpStatus.OK, "200", "유저 로그아웃에 성공했습니다."),
    SUCCESS_FIND_USER_APPLY_PROGRAMS(HttpStatus.OK, "200", "유저가 신청한 프로그램 조회에 성공했습니다.");

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