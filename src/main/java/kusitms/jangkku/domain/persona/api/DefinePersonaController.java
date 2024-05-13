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

    // 현재 페르소나 결과를 반환하는 API
    @PostMapping("/define")
    public ResponseEntity<ApiResponse<DefinePersonaDto.DefinePersonaResponse>> getDefinePersona(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody DefinePersonaDto.DefinePersonaRequest definePersonaRequest) {

        DefinePersonaDto.DefinePersonaResponse definePersonaResponse = definePersonaService.getDefinePersona(authorizationHeader, definePersonaRequest);
        return ApiResponse.onSuccess(PersonaSuccessStatus.CREATED_DEFINE_PERSONA, definePersonaResponse);
    }
}