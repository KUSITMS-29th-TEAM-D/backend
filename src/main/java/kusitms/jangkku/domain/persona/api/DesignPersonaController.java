package kusitms.jangkku.domain.persona.api;

import kusitms.jangkku.domain.persona.application.DesignPersonaService;
import kusitms.jangkku.domain.persona.constant.PersonaSuccessStatus;
import kusitms.jangkku.domain.persona.dto.DesignPersonaDto;
import kusitms.jangkku.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class DesignPersonaController {
    private final DesignPersonaService designPersonaService;

    // 설계하기 페르소나를 생성하는 API
    @PostMapping("/design")
    public ResponseEntity<ApiResponse<DesignPersonaDto.DesignPersonaResponse>> createDesignPersona(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DesignPersonaDto.DesignPersonaRequest designPersonaRequest) {

        DesignPersonaDto.DesignPersonaResponse designPersonaResponse = designPersonaService.createDesignPersona(authorizationHeader, designPersonaRequest);

        return ApiResponse.onSuccess(PersonaSuccessStatus.CREATED_DESIGN_PERSONA, designPersonaResponse);
    }

    // 설계하기 페르소나를 조회하는 API
    @GetMapping("/design")
    public ResponseEntity<ApiResponse<DesignPersonaDto.DesignPersonaDetailResponse>> getDesignPersona(
            @RequestHeader("Authorization") String authorizationHeader) {

        DesignPersonaDto.DesignPersonaDetailResponse designPersonaDetailResponse = designPersonaService.getDesignPersona(authorizationHeader);

        return ApiResponse.onSuccess(PersonaSuccessStatus.GET_DESIGN_PERSONA, designPersonaDetailResponse);
    }
}