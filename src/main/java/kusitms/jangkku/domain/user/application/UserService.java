package kusitms.jangkku.domain.user.application;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.user.dto.UserDto;

public interface UserService {
    UserDto.UserRegisterResponse registerUser(HttpServletResponse response, String authorizationHeader, UserDto.UserRegisterRequest userRegisterRequest);
}