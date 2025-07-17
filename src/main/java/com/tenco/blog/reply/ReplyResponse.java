package com.tenco.blog.reply;

import lombok.Data;

public class ReplyResponse {

    @Data
    public static class SaveDTO {
        private Long id;
        private String comment;
        private String writerName;
        private String createdAt;
        private Long boardId;

        public SaveDTO(Reply reply) {
            this.id = reply.getId();
            this.comment = reply.getComment();
            this.writerName = reply.getUser().getUsername();
            this.createdAt = reply.getCreatedAt().toString();
            this.boardId = reply.getBoard().getId();
        }
    }


}
