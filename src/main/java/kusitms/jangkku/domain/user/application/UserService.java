package kusitms.jangkku.domain.user.application;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.user.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    UserDto.UserRegisterResponse registerUser(HttpServletResponse response, String authorizationHeader, UserDto.UserRegisterRequest userRegisterRequest);
    boolean isDuplicate(String nickname);
    void uploadProfileImg(String authorizationHeader, MultipartFile file);
    UserDto.UserInfosResponse getUserInfos(String authorizationHeader);
    UserDto.UserInfosResponse editUserInfos(String authorizationHeader, UserDto.EditUserInfosRequest editUserInfosRequest);
    void logout(HttpServletResponse response);
    List<UserDto.userHomeResponse> findUserApplyPrograms(String authorizationHeader, String type,String sort);
}