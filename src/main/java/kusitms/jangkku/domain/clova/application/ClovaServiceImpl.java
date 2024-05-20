package kusitms.jangkku.domain.clova.application;

import kusitms.jangkku.domain.clova.dto.ClovaDto;
import kusitms.jangkku.domain.clova.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClovaServiceImpl implements ClovaService {

    @Value("${clova.api.url}")
    public String apiUrl;
    @Value("${clova.api.api-key}")
    private String apiKey;
    @Value("${clova.api.api-gateway-key}")
    private String apiGatewayKey;
    private final WebClient webClient;

    // 설계하기 페르소나를 CLOVA로 생성하는 메서드
    @Override
    public String createDesignPersona(String message) {
        ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.DesignPersonaRequestOf();
        request.getMessages().add(Message.creatUserOf(message));

        return requestWebClient(request);
    }

    // 돌아보기 페르소나 공감을 생성하는 메서드
    @Override
    public String createDiscoverPersonaReaction(String message) {
        ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.DiscoverPersonaReactionRequestOf();
        request.getMessages().add(Message.creatUserOf(message));

        return requestWebClient(request);
    }

    // 돌아보기 페르소나 요약을 생성하는 메서드
    @Override
    public String createDiscoverPersonaSummary(String message) {
        ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.DiscoverPersonaSummaryRequestOf();
        request.getMessages().add(Message.creatUserOf(message));

        return requestWebClient(request);
    }

    // CLOVA와 통신하여 답변을 가져오는 메서드
    public String requestWebClient(ClovaDto.ChatBotRequestDto request) {
        ClovaDto.ChatBotResponse message = webClient.post()
                .uri(apiUrl)
                .header("X-NCP-CLOVASTUDIO-API-KEY", apiKey)
                .header("X-NCP-APIGW-API-KEY", apiGatewayKey)
                .header("Content-Type", "application/json")
                .body(Mono.just(request), request.getClass())
                .retrieve()
                .bodyToMono(ClovaDto.ChatBotResponse.class)
                .block();

        return message.getResult().getMessage().getContent();
    }
}