package kusitms.jangkku.domain.program.api;

import kusitms.jangkku.domain.program.application.ProgramService;
import kusitms.jangkku.domain.program.dto.ProgramDetailDto;
import kusitms.jangkku.domain.program.dto.ProgramDto;
import kusitms.jangkku.domain.program.dto.ProgramsHomeDto;
import kusitms.jangkku.global.common.ApiResponse;
import kusitms.jangkku.global.common.constant.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kusitms.jangkku.domain.program.constant.ProgramSuccessStatus.PROGRAM_APPLY_CREATED;
import static kusitms.jangkku.domain.program.dto.ProgramDto.*;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    //자기이해 조회 - 전체
    @GetMapping("/main/self-understanding")
    public ResponseEntity<ApiResponse<List<ProgramsMainResponseDto>>> findMainSelfUnderstanding() {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMainSelfUnderstanding());
    }

    //브랜딩 조회 - 전체
    @GetMapping("/main/branding")
    public ResponseEntity<ApiResponse<List<ProgramsMainResponseDto>>> findMainBranding(@RequestHeader("Authorization") String authorizationHeader) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMainBranding(authorizationHeader));
    }

    //자기이해 조회 - 필터링
    @PostMapping("/more/self-understanding")
    private ResponseEntity<ApiResponse<List<ProgramsMainResponseDto>>> findMoreSelfUnderstanding(@RequestBody ProgramSelfUnderstandingRequestDto requestDto) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMoreSelfUnderstanding(requestDto));
    }

    //브랜딩 조회 - 필터링
    @PostMapping("/more/branding")
    private ResponseEntity<ApiResponse<List<ProgramsMainResponseDto>>> findMoreSelfUnderstanding(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProgramBrandingRequestDto requestDto) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getMoreBranding(authorizationHeader,requestDto));
    }

    //프로그램 상세조회
    @GetMapping("/{type}/{program_id}")
    private ResponseEntity<ApiResponse<ProgramDetailDto.ProgramDetailResponseDto>> findDetailProgram(@RequestHeader("Authorization") String authorizationHeader,
                                                                                                     @PathVariable(name = "type") String type,@PathVariable(name = "program_id") Long programId) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getDetailProgram(authorizationHeader, programId, type));
    }

    //메인페이지 자기이해 조회
    @PostMapping("/home/self-understanding")
    private ResponseEntity<ApiResponse<List<ProgramsHomeDto.ProgramsHomeResponseDto>>> findHomeSelfUnderstanding(@RequestHeader("Authorization") String authorizationHeader,@RequestBody ProgramSelfUnderstandingRequestDto requestDto) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getHomeSelfUnderstanding(authorizationHeader,requestDto));
    }

    //메인페이지 브랜딩 조회
    @PostMapping("/home/branding")
    private ResponseEntity<ApiResponse<List<ProgramsHomeDto.ProgramsHomeResponseDto>>> findHomeSelfUnderstanding(@RequestHeader("Authorization") String authorizationHeader,@RequestBody ProgramBrandingRequestDto requestDto) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getHomeBranding(authorizationHeader,requestDto));
    }

    //메인페이지 비로그인 브랜딩 조회
    @PostMapping("/branding/non-login")
    private ResponseEntity<ApiResponse<List<ProgramsHomeDto.ProgramsHomeResponseDto>>> findHomeBrandingNonLogin(@RequestBody ProgramBrandingRequestDto requestDto) {
        return ApiResponse.onSuccess(SuccessStatus._OK, programService.getHomeBrandingNonLogin(requestDto));
    }

    //프로그램 신청하기
    @PostMapping("/apply")
    private ResponseEntity<ApiResponse<Object>> applyPrograms(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProgramDto.ProgramJoinRequestDto requestDto) {
        programService.applyPrograms(authorizationHeader,requestDto);
        return ApiResponse.onSuccess(PROGRAM_APPLY_CREATED);
    }
}