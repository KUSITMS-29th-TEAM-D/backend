package kusitms.jangkku.domain.chatbot.application;

import kusitms.jangkku.domain.chatbot.dto.ChatBotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ChatBotService {

    @Value("${clova.api.url}")
    public String apiUrl;
    @Value("${clova.api.api-key}")
    private String apiKey;
    @Value("${clova.api.api-gateway-key}")
    private String apiGatewayKey;

    @Autowired
    WebClient webClient;

    public String requestChatBot(ChatBotDto.ChatBotRequestDto mess) {
        ChatBotDto.ChatBotResponse message = webClient.post()
                .uri(apiUrl)
                .header("X-NCP-CLOVASTUDIO-API-KEY", apiKey)
                .header("X-NCP-APIGW-API-KEY", apiGatewayKey)
                .header("Content-Type", "application/json")
                .body(Mono.just(mess), mess.getClass())
                .retrieve()
                .bodyToMono(ChatBotDto.ChatBotResponse.class)
                .block();

        return message.getResult().getMessage().getContent();
    }
}
