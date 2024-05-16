package kusitms.jangkku.domain.chatbot.api;

import jakarta.servlet.http.HttpSession;
import kusitms.jangkku.domain.chatbot.application.ChatBotService;
import kusitms.jangkku.domain.chatbot.dto.ChatBotUserDto;
import kusitms.jangkku.global.common.ApiResponse;
import kusitms.jangkku.global.common.constant.SuccessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/chat-bot")
@Slf4j
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> testChatBotController(@RequestBody ChatBotUserDto.ChatBotUserRequestDto requestDto, HttpSession session){
        return ApiResponse.onSuccess(SuccessStatus._OK,chatBotService.requestChatBot(requestDto,session)) ;
    }
}
