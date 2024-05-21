package kusitms.jangkku.domain.program.application;

import kusitms.jangkku.domain.program.dto.ProgramDetailDto;
import kusitms.jangkku.domain.program.dto.ProgramDto;

import java.util.List;

public interface ProgramService {
    List<ProgramDto.ProgrmsMainResponsetDto> getMainSelfUnderstanding();

    List<ProgramDto.ProgrmsMainResponsetDto> getMainBranding(String authorizationHeader);

    List<ProgramDto.ProgrmsMainResponsetDto> getMoreSelfUnderstanding(ProgramDto.ProgramSelfUnderstandingRequestDto requestDto);

    List<ProgramDto.ProgrmsMainResponsetDto> getMoreBranding(String authorizationHeader,ProgramDto.ProgramBrandingRequestDto requestDto);

    ProgramDetailDto.ProgramDetailResponseDto getDetailProgram(String authorizationHeader, Long programId, String form);
}
