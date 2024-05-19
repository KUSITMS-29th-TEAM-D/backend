package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.persona.dto.DiscoverPersonaDto;

public interface DiscoverPersonaService {
    DiscoverPersonaDto.QuestionResponse getNewQuestion(String authorizationHeader, String category);
}
