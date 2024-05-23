package kusitms.jangkku.domain.user.api;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.jangkku.domain.user.application.UserService;
import kusitms.jangkku.domain.user.constant.UserSuccessStatus;
import kusitms.jangkku.domain.user.dto.UserDto;
import kusitms.jangkku.domain.user.exception.UserErrorResult;
import kusitms.jangkku.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kusitms.jangkku.domain.user.constant.UserSuccessStatus.SUCCESS_FIND_USER_APPLY_PROGRAMS;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 기본 정보를 입력 받아 저장하는 API
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto.UserRegisterResponse>> registerUser(
            HttpServletResponse response,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserDto.UserRegisterRequest userRegisterRequest) {

        UserDto.UserRegisterResponse userRegisterResponse = userService.registerUser(response, authorizationHeader, userRegisterRequest);
        return ApiResponse.onSuccess(UserSuccessStatus.SUCCESS_REGISTER_USER, userRegisterResponse);
    }

    // 닉네임 중복 여부를 반환하는 API
    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<ApiResponse<Object>> checkNickname(
            @PathVariable("nickname") String nickname) {

        if (!userService.isDuplicate(nickname)) {
            return ApiResponse.onSuccess(UserSuccessStatus.IS_NICKNAME_POSSIBLE);
        } else {
            return ApiResponse.onFailure(UserErrorResult.IS_DUPLICATE_NICKNAME);
        }
    }

    // 프로필 사진을 업로드 하는 API
    @PostMapping("/profile-img")
    public ResponseEntity<ApiResponse<Object>> uploadProfileImg(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("file") MultipartFile file) {

        userService.uploadProfileImg(authorizationHeader, file);

        return ApiResponse.onSuccess(UserSuccessStatus.SUCCESS_UPLOAD_PROFILE_IMG);
    }

    // 유저 정보를 조회하는 API
    @GetMapping("/infos")
    public ResponseEntity<ApiResponse<Object>> getUserInfos(
            @RequestHeader("Authorization") String authorizationHeader) {

        UserDto.UserInfosResponse userInfosResponse = userService.getUserInfos(authorizationHeader);

        return ApiResponse.onSuccess(UserSuccessStatus.SUCCESS_GET_USER_INFOS, userInfosResponse);
    }

    // 유저 정보를 수정하는 API
    @PatchMapping("/infos")
    public ResponseEntity<ApiResponse<Object>> editUserInfos(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserDto.EditUserInfosRequest editUserInfosRequest) {

        UserDto.UserInfosResponse userInfosResponse = userService.editUserInfos(authorizationHeader, editUserInfosRequest);

        return ApiResponse.onSuccess(UserSuccessStatus.SUCCESS_EDIT_USER_INFOS, userInfosResponse);
    }

    // 유저 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(
            HttpServletResponse response) {

        userService.logout(response);

        return ApiResponse.onSuccess(UserSuccessStatus.SUCCESS_LOGOUT);
    }

    //마이페이지 신청한 경험 조회 API
    @GetMapping("/experiences/{type}/{sort}")
    public ResponseEntity<ApiResponse<List<UserDto.userHomeResponse>>> findUserApplyPrograms(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String type,@PathVariable String sort) {
        return ApiResponse.onSuccess(SUCCESS_FIND_USER_APPLY_PROGRAMS,userService.findUserApplyPrograms(authorizationHeader,type,sort));
    }


}