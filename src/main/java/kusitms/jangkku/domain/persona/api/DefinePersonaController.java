package kusitms.jangkku.domain.persona.api;

import kusitms.jangkku.domain.persona.application.DefinePersonaService;
import kusitms.jangkku.domain.persona.constant.PersonaSuccessStatus;
import kusitms.jangkku.domain.persona.dto.DefinePersonaDto;
import kusitms.jangkku.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class DefinePersonaController {
    private final DefinePersonaService definePersonaService;

    // 정의하기 페르소나를 생성하는 API (로그인 유저)
    @PostMapping("/define")
    public ResponseEntity<ApiResponse<DefinePersonaDto.DefinePersonaResponse>> createDefinePersona(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {

        DefinePersonaDto.DefinePersonaResponse definePersonaResponse = definePersonaService.createDefinePersona(authorizationHeader, definePersonaRequest);

        return ApiResponse.onSuccess(PersonaSuccessStatus.CREATED_DEFINE_PERSONA, definePersonaResponse);
    }

    // 정의하기 페르소나를 생성하는 API (비로그인 유저)
    @PostMapping("/define/sharing")
    public ResponseEntity<ApiResponse<DefinePersonaDto.DefinePersonaResponse>> createDefinePersonaForSharing(
            @RequestBody DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {

        DefinePersonaDto.DefinePersonaResponse definePersonaResponse = definePersonaService.createDefinePersonaForSharing(definePersonaRequest);

        return ApiResponse.onSuccess(PersonaSuccessStatus.CREATED_DEFINE_PERSONA, definePersonaResponse);
    }

    // 정의하기 페르소나 결과를 반환하는 API (로그인 유저)
    @GetMapping("/define")
    public ResponseEntity<ApiResponse<DefinePersonaDto.DefinePersonaResponse>> getDefinePersona(
            @RequestHeader("Authorization") String authorizationHeader) {

        DefinePersonaDto.DefinePersonaResponse definePersonaResponse = definePersonaService.getDefinePersona(authorizationHeader);

        return ApiResponse.onSuccess(PersonaSuccessStatus.GET_DEFINE_PERSONA, definePersonaResponse);
    }

    // 정의하기 페르소나 결과를 반환하는 API (비로그인 유저)
    @GetMapping("/define/sharing")
    public ResponseEntity<ApiResponse<DefinePersonaDto.DefinePersonaResponse>> getDefinePersonaForSharing(
            @RequestParam("define_persona_id") String definePersonaId) {

        DefinePersonaDto.DefinePersonaResponse definePersonaResponse = definePersonaService.getDefinePersonaForSharing(definePersonaId);

        return ApiResponse.onSuccess(PersonaSuccessStatus.GET_DEFINE_PERSONA, definePersonaResponse);
    }
}