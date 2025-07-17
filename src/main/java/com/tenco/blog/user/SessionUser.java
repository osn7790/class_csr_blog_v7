package com.tenco.blog.user;

import lombok.Builder;
import lombok.Data;

@Data
public class SessionUser {
    private Long id;
    private String username;
    private String email;

    @Builder
    public SessionUser(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Builder
    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }


}
