package kusitms.jangkku.domain.user.application;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.user.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDto.UserRegisterResponse registerUser(HttpServletResponse response, String authorizationHeader, UserDto.UserRegisterRequest userRegisterRequest);
    boolean isDuplicate(String nickname);
    void uploadProfileImg(String authorizationHeader, MultipartFile file);
}