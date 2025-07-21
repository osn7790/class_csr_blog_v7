package com.tenco.blog.user;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.utils.Define;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관리 API")
@RequiredArgsConstructor
@RestController // @Controller + @ResponseBody
public class UserRestController {


    private final UserService userService;


    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO joinDTO, Errors errors) {

        UserResponse.JoinDTO joinedUser = userService.join(joinDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(joinedUser));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO,
                                   Errors errors) {

        String jwtToken = userService.login(loginDTO);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(new ApiUtil<>(null));
    }

    // 회원 정보 조회 API (JWT 인증 필요)
    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "id") Long id,
                                         @RequestAttribute(Define.SESSION_USER) SessionUser sessionUser) {
        // 인증체크
        if (sessionUser == null) {
            throw new Exception401("인증 정보가 없습니다");
        }
        UserResponse.DetailDTO userDetail = userService.findUserById(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(userDetail));
    }

    // 회원 정보 수정 API (JWT 인증 필요)
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable(name = "id") Long id,
                                            @RequestAttribute(Define.SESSION_USER) SessionUser sessionUser,
                                            @Valid @RequestBody UserRequest.UpdateDTO updateDTO,
                                            Errors errors) {
        // 인증체크
        if (sessionUser == null) {
            throw new Exception401("인증 정보가 없습니다");
        }

        UserResponse.UpdateDTO updateUser = userService.updateById(id, sessionUser.getId(), updateDTO);
        return ResponseEntity.ok().body(new ApiUtil<>(updateUser));
    }

    // 클라이언트 단에서 jwt 토큰 정보를 직접 삭제 처리 한다.
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new ApiUtil<>("로그아웃 성공"));
    }


}
