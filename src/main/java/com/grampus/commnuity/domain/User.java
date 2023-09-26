package com.grampus.commnuity.domain;

import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String loginId; // 로그인 시 사용하는 아이디

    private String password; // 비밀번호

    private String username; // 닉네임

    private Role role; // role : USER, ADMIN

}