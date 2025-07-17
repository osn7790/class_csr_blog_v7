package com.tenco.blog.reply;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.board.Board;
import com.tenco.blog.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty
        private Long boardId;
        @NotEmpty(message = "댓글 내용을 입력해야 합니다.")
        @Size(max = 500, message = "댓글은 500자 이내로 작성해주세요.")
        private String comment;



        /**
         * 보통 SAVE DTO에 toEntity 메서드를 만들게 된다
         * 멤버 변수에 없는 데이터가 필요할 때는
         * 외부에서 주입 받으면 된다.
         */
        public Reply toEntity(User sessionUser, Board board) {
            return Reply.builder()
                    .comment(comment.trim())
                    .user(sessionUser)
                    .board(board)
                    .build();
        }

    }


}
