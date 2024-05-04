package kusitms.jangkku.domain.user.api;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.user.application.UserService;
import kusitms.jangkku.domain.user.dto.UserDto;
import kusitms.jangkku.global.common.ApiResponse;
import kusitms.jangkku.global.common.constant.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 기본 정보를 입력 받아 저장하는 API
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto.UserRegisterResponse>> RegisterUser(
            HttpServletResponse response,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserDto.UserRegisterRequest userRegisterRequest) {

        UserDto.UserRegisterResponse userRegisterResponse = userService.registerUser(response, authorizationHeader, userRegisterRequest);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_REGISTER_USER, userRegisterResponse);
    }
}