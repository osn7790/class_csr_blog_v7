package com.tenco.blog.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        @NotEmpty(message = "사용자명을 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자/ 2~20자 이내로 작성해주세요")
        private String username;
        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용 x
        @Size(min = 4, max = 20, message = "비밀번호 4~20자 이내로 작성해주세요")
        private String password;
        @NotEmpty(message = "이메일은 필수입니다")  // 필수 입력 검증
        @Pattern(
                regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요"
        )
        private String email;

        // JoinDTO 를 User Object 변환 하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .build();
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        @NotEmpty(message = "사용자명을 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자/ 2~20자 이내로 작성해주세요")
        private String username;

        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용 x
        @Size(min = 4, max = 20, message = "비밀번호 4~20자 이내로 작성해주세요")
        private String password;



    }

    @Data
    public static class UpdateDTO {

        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용 x
        @Size(min = 4, max = 20, message = "비밀번호 4~20자 이내로 작성해주세요")
        private String password;
        @NotEmpty(message = "이메일은 필수입니다")  // 필수 입력 검증
        @Pattern(
                regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요"
        )
        private String email;

    }

}
