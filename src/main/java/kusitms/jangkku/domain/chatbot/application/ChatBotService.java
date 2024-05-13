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
    @Value("${clova.api.request-id}")
    private String requestId;

    @Autowired
    WebClient webClient;

    public String requestChatBot(ChatBotDto.ChatBotRequestDto mess) {
        Mono<ChatBotDto.ChatBotResponse> message = webClient.post()
                .uri(apiUrl)
                .headers(headers -> headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey))
                .headers(headers -> headers.set("X-NCP-APIGW-API-KEY", apiGatewayKey))
                .headers(headers -> headers.set("Content-Type", "application/json"))
                .body(Mono.just(mess), mess.getClass())
                .retrieve()
                .bodyToMono(ChatBotDto.ChatBotResponse.class);
        System.out.println("message : " + message);
//        System.out.println("message : " + message.subscribe(System.out::println));
//        return "message : "+message.block().getContent();
        return "message : " + message;
    }
}
