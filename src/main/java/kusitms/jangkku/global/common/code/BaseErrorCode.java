package kusitms.jangkku.global.common.code;

import kusitms.jangkku.global.common.dto.ErrorReasonDto;

public interface BaseErrorCode {
    public ErrorReasonDto getReason();

    public ErrorReasonDto getReasonHttpStatus();
}