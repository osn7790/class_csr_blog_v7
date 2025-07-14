package com.tenco.blog.user;

import lombok.Builder;
import lombok.Data;

public class UserResponse {

    // 회원 가입 후 응답 DTO
    @Data
    public static class JoinDTO {
        private Long id;
        private String username;
        private String email;
        private String createdAt;

        // 서버단에서 new UserResponse.JoinDTO(...)
        @Builder
        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.createdAt = user.getCreatedAt().toString();
        }
    } // end of inner class

    // 로그인 후 응답 DTO
    @Data
    public static class LoginDTO {
        private Long id;
        private String username;
        private String email;

        @Builder
        public LoginDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    // 회원정보 수정 응답 DTO 설계
    @Data
    public static class UpdateDTO {
        private Long id;
        private String username;
        private String email;

        @Builder
        public UpdateDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    @Data
    public static class DetailDTO {
        private Long id;
        private String username;
        private String email;

        @Builder
        public DetailDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
