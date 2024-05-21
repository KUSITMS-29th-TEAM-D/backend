package kusitms.jangkku.domain.program.api;

import kusitms.jangkku.domain.program.dto.ProgramDetailDto;
import kusitms.jangkku.domain.program.application.ProgramServiceImpl;
import kusitms.jangkku.global.common.ApiResponse;
import kusitms.jangkku.global.common.constant.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kusitms.jangkku.domain.program.dto.ProgramDto.*;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramServiceImpl programService;

    //자기이해 조회 - 전체
    @GetMapping("/main/self-understanding")
    public ResponseEntity<ApiResponse<List<ProgrmsMainResponsetDto>>> findMainSelfUnderstanding() {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMainSelfUnderstanding());
    }

    //브랜딩 조회 - 전체
    @GetMapping("/main/branding")
    public ResponseEntity<ApiResponse<List<ProgrmsMainResponsetDto>>> findMainBranding(@RequestHeader("Authorization") String authorizationHeader) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMainBranding(authorizationHeader));
    }

    //자기이해 조회 - 필터링
    @PostMapping("/more/self-understanding")
    private ResponseEntity<ApiResponse<List<ProgrmsMainResponsetDto>>> findMoreSelfUnderstanding(@RequestBody ProgramSelfUnderstandingRequestDto requestDto) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMoreSelfUnderstanding(requestDto));
    }

    //브랜딩 조회 - 필터링
    @PostMapping("/more/branding")
    private ResponseEntity<ApiResponse<List<ProgrmsMainResponsetDto>>> findMoreSelfUnderstanding(@RequestHeader("Authorization") String authorizationHeader,@RequestBody ProgramBrandingRequestDto requestDto) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMoreBranding(authorizationHeader,requestDto));
    }

    //프로그램 상세조회
    @GetMapping("/{form}/{programId}")
    private ResponseEntity<ApiResponse<ProgramDetailDto.ProgramDetailResponseDto>> findDetailProgram(@RequestHeader("Authorization") String authorizationHeader,
                                                                                                     @PathVariable(name = "form") String form,@PathVariable(name = "program_id") Long programId) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getDetailProgram(authorizationHeader, programId, form));
    }

}

