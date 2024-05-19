package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.persona.dto.DiscoverPersonaDto;

public interface DiscoverPersonaService {
    DiscoverPersonaDto.QuestionResponse getNewQuestion(String authorizationHeader, String category);
    DiscoverPersonaDto.AnswerResponse getReactionAndSummary(String authorizationHeader, DiscoverPersonaDto.AnswerRequest answerRequest);
    DiscoverPersonaDto.ChattingResponse getChattings(String authorizationHeader, String category);
    DiscoverPersonaDto.SummaryResponse getSummaries(String authorizationHeader);
}