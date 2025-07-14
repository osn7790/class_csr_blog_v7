package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service // IoC
@Transactional(readOnly = true) // 클래스 레벨에서의 읽기 전용 설정
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserJpaRepository userJpaRepository;


    /**
     * 회원가입 처리 - DTO 변환 책임
     */
    @Transactional // 메서드 레벨에서 쓰기 전용 트랜잭션 활성화
    public UserResponse.JoinDTO join(UserRequest.JoinDTO joinDTO) {
        //1. 사용자명 중복 체크
        userJpaRepository.findByUsername(joinDTO.getUsername())
                .ifPresent(user1 -> {
                    throw new Exception400("이미 존재하는 사용자명입니다");
                });
        // 2. 회원 가입 처리
        User savedUser = userJpaRepository.save(joinDTO.toEntity());
        // 3. 응답 DTO로 변환 해서 반환 일을 시킴
        return  new UserResponse.JoinDTO(savedUser);
    }

    /**
     * 로그인 처리 - 응답 DTO 변환 책임
     */
    public UserResponse.LoginDTO login(UserRequest.LoginDTO loginDTO) {
        // 회원 정보 조회
        User selectedUser = userJpaRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> {
                    throw new Exception400("사용자명 또는 비밀번호가 틀렸어요");
                });
        // User 엔티티를 응답 DTO 변환해서 반환 처리
        return new UserResponse.LoginDTO(selectedUser);
    }

    /**
     *  사용자 정보 조회 - 응답 DTO 반환 책임
     */
    public UserResponse.DetailDTO findById(Long id) {
        // 인증 처리 --> 세션 기반 (JWT 추후 변경)
        // 권한 검사 일단 생략 ...
        User selectedUser = userJpaRepository.findById(id).orElseThrow(() -> {
            log.warn("사용자 조회 실패 - ID {}", id);
            return new Exception404("사용자를 찾을 수 없습니다");
        });
        // 응답 DTO 변환 처리
         return new UserResponse.DetailDTO(selectedUser);
    }

    /**
     *  회원정보 수정 처리 - DTO 변환 책임
     */
    @Transactional
    public UserResponse.UpdateDTO updateById(Long userId, UserRequest.UpdateDTO updateDTO) {
        log.info("회원정보 수정 서비스 처리 시작 - ID : {}",  userId);

        // 1. 권한 체크
        User selectedUser = userJpaRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new Exception404("사용자를 찾을 수 없습니다");
                });
        // 2. 더티 체킹을 통한 회원 정보 수정
        selectedUser.update(updateDTO);

        // 3. 응답 DTO 반환
        return new UserResponse.UpdateDTO(selectedUser);
    }
}
