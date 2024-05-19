package kusitms.jangkku.domain.persona.api;

import kusitms.jangkku.domain.persona.application.DiscoverPersonaService;
import kusitms.jangkku.domain.persona.constant.PersonaSuccessStatus;
import kusitms.jangkku.domain.persona.dto.DiscoverPersonaDto;
import kusitms.jangkku.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class DiscoverPersonaController {
    private final DiscoverPersonaService discoverPersonaService;

    // 설계하기 페르소나를 조회하는 API
    @GetMapping("/discover/question")
    public ResponseEntity<ApiResponse<DiscoverPersonaDto.QuestionResponse>> getDesignPersona(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("category") String category) {

        DiscoverPersonaDto.QuestionResponse questionResponse = discoverPersonaService.getNewQuestion(authorizationHeader, category);

        return ApiResponse.onSuccess(PersonaSuccessStatus.GET_NEW_QUESTION, questionResponse);
    }
}