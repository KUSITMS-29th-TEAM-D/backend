package kusitms.jangkku.domain.clova.application;

import kusitms.jangkku.domain.clova.dto.ClovaDto;

public interface ClovaService {
    String requestWebClient(ClovaDto.ChatBotRequestDto request);
    String createDesignPersona(String message);
}