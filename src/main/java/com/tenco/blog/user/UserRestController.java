package com.tenco.blog.user;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController // @Controller + @ResponseBody
public class UserRestController {

    // @Slf4j 사용지 자동 선언 됨
    // private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private final UserService userService;
    // 생성자 의존 주입 - DI
    //UserService userService = new UserService();
    //    public UserRestController(UserService userService) {
    //        this.userService = userService;
    //    }

    // http://localhost:8080/join
    // 회원 가입 API 설계
    @PostMapping("/join")
    //public ResponseEntity<ApiUtil<UserResponse.JoinDTO>> join() {
    // JSON 형식에 데이터를 추출 할 때 @RequestBody 선언
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
        log.info("회원가입 API 호출 - 사용자명:{}, 이메일:{}",
                reqDTO.getUsername(), reqDTO.getEmail());
        reqDTO.validate();
        // 서비스에 위임 처리
        UserResponse.JoinDTO joinUser = userService.join(reqDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiUtil<>(joinUser));
    }


    // 로그인 API
    // http://localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<ApiUtil<UserResponse.LoginDTO>> login(
            @RequestBody UserRequest.LoginDTO reqDTO, HttpSession session) {

        log.info("로그인 API 호출 - 사용자명: {}", reqDTO.getUsername());
        reqDTO.validate();
        UserResponse.LoginDTO loginUser = userService.login(reqDTO);
        session.setAttribute(Define.SESSION_USER, loginUser);

        return ResponseEntity.ok(new ApiUtil<>(loginUser));

    }


    // 회원 정보 조회
    // public ResponseEntity<?> detail(
    @GetMapping("api/users/{id}")
    public ResponseEntity<ApiUtil<UserResponse.DetailDTO>> getUserInfo(
            @PathVariable(name = "id") Long id, HttpSession session) {

        log.info("회원 정보 API 호출 - ID: {}", id);

        User sessionUser = (User)session.getAttribute(Define.SESSION_USER);

        // 로그인한 사용자 - 10
        // 로그인한 사용자(10번) - 30 (정보요청가능)
        userService.findUserById(id, sessionUser);

        return ResponseEntity.ok(new ApiUtil<>(userService.findById(id)));
    }

    // 회원 정보 수정
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> updateUser (@PathVariable(name = "id")Long id,
                                         @RequestBody UserRequest.UpdateDTO updateDTO){
        log.info("회원 정보 수정 API 호출 - ID: {}", id);

        // 인증검사는 인터셉터에서 처리 됨.
        // 유효성 검사
        updateDTO.validate();
        UserResponse.UpdateDTO updateUser = userService.updateById(id, updateDTO);

        return ResponseEntity.ok(new ApiUtil<>(updateUser));
    }


    // 로그아웃 처리
    @GetMapping("/logout")

    public ResponseEntity<ApiUtil<String>> logout(HttpSession session) {
        log.info("로그아웃 API 호출");
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil<>("로그아웃 성공"));
    }

}
