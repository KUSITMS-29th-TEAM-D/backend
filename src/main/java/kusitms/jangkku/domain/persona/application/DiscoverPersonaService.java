package kusitms.jangkku.domain.persona.application;

import kusitms.jangkku.domain.persona.dto.DiscoverPersonaDto;
import org.springframework.stereotype.Service;

@Service
public interface DiscoverPersonaService {
    DiscoverPersonaDto.QuestionResponse getNewQuestion(String authorizationHeader, String category);
    DiscoverPersonaDto.AnswerResponse getReactionAndSummary(String authorizationHeader, DiscoverPersonaDto.AnswerRequest answerRequest);
    DiscoverPersonaDto.ChattingResponse getChattings(String authorizationHeader, String category);
    DiscoverPersonaDto.SummaryResponse getSummaries(String authorizationHeader);
    void restartChatting(String authorizationHeader, DiscoverPersonaDto.resetChattingRequest resetChattingRequest);
    DiscoverPersonaDto.CheckCompleteResponse checkComplete(String authorizationHeader);
    DiscoverPersonaDto.KeywordResponse getAllKeywords(String authorizationHeader);
    DiscoverPersonaDto.KeywordResponse getCategoryKeywords(String authorizationHeader, String category);
}