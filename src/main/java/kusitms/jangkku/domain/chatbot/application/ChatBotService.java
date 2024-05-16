package kusitms.jangkku.domain.chatbot.application;

import jakarta.servlet.http.HttpSession;
import kusitms.jangkku.domain.chatbot.dto.ChatBotDto;
import kusitms.jangkku.domain.chatbot.dto.ChatBotUserDto;
import kusitms.jangkku.domain.chatbot.dto.Message;
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

    //첫 요청이면 ChatBotRequestDto.of()함수 생성 -> 사용자 메시지 추가 -> webclient 호출 -> CLOVAStudio 응답 추가 -> 세션에 추가
    //두번째 요청부터는 기존 세션에서 dto 찾기 -> 그 dto에 사용자 메시지 추가 -> webclinet 호출 ->  CLOVAStudio 응답 추가 -> 세션에 다시 추가

    public String requestChatBot(ChatBotUserDto.ChatBotUserRequestDto requestDto, HttpSession session) {
        ChatBotDto.ChatBotRequestDto request = checkIsStart(requestDto.isStart, session);
        request.getMessages().add(Message.creatUserOf(requestDto.getContent()));
        String chatbotRequest = requestWebClient(request);
        request.getMessages().add(Message.creatSystemOf(chatbotRequest));
        session.setAttribute("chatBot", request);
        System.out.println("request : " + request.getMessages());

        return chatbotRequest;
    }

    public ChatBotDto.ChatBotRequestDto checkIsStart(boolean isStart, HttpSession session) {
        if (isStart) return ChatBotDto.ChatBotRequestDto.of();
        else return (ChatBotDto.ChatBotRequestDto) session.getAttribute("chatBot");
    }

    public String requestWebClient(ChatBotDto.ChatBotRequestDto request) {
        ChatBotDto.ChatBotResponse message = webClient.post()
                .uri(apiUrl)
                .header("X-NCP-CLOVASTUDIO-API-KEY", apiKey)
                .header("X-NCP-APIGW-API-KEY", apiGatewayKey)
                .header("Content-Type", "application/json")
                .body(Mono.just(request), request.getClass())
                .retrieve()
                .bodyToMono(ChatBotDto.ChatBotResponse.class)
                .block();

        return message.getResult().getMessage().getContent();
    }

    // 설계하기 페르소나를 CLOVA로 생성하는 메서드
    public String createDesignPersona(String message) {
        ChatBotDto.ChatBotRequestDto request = ChatBotDto.ChatBotRequestDto.createDesignPersonaRequest();
        request.getMessages().add(Message.creatUserOf(message));
        String designPersonaResult = requestWebClient(request);
        System.out.println(designPersonaResult);

        return designPersonaResult;
    }
}