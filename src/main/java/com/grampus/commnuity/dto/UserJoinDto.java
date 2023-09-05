package com.grampus.commnuity.dto;

import com.grampus.commnuity.domain.Role;
import com.grampus.commnuity.domain.User;
import lombok.Data;

@Data
public class UserJoinDto {
    private String loginId;
    private String password;
    private String username;

    public User toEntity(String encodedPassword){
        return User.builder().loginId(loginId).username(username).password(encodedPassword).role(Role.USER).build();
    }
}
