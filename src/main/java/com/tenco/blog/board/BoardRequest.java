package com.tenco.blog.board;

import com.tenco.blog.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 클라이언트에게 넘어온 데이터를
 * Object로 변화해서 전달하는 DTO 역할을 담당한다
 */
public class BoardRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty(message = "제목은 필수입니다.")
        @Size(max = 30, message = "제목은 30자 이내로만 작성 가능합니다.")
        private String title;
        @NotEmpty(message = "내용은 필수입니다.")
        @Size(max = 500, message = "내용은 500자 이내로만 작성 가능합니다.")
        private String content;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(this.title)
                    .user(user)
                    .content(this.content)
                    .build();
        }

    }

    @Data
    public static class UpdateDTO {
        @NotEmpty(message = "제목은 필수입니다.")
        @Size(max = 30, message = "제목은 30자 이내로만 작성 가능합니다.")
        private String title;
        @NotEmpty(message = "내용은 필수입니다.")
        @Size(max = 500, message = "내용은 500자 이내로만 작성 가능합니다.")
        private String content;


    }


}
