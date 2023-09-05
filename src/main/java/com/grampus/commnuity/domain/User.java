package com.grampus.commnuity.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id @GeneratedValue
    @Column(name="user_id")
    private Long id;

    private String loginId; // 로그인 시 사용하는 아이디
    private String password; // 비밀번호
    private String username; // 닉네임

    @Enumerated(EnumType.STRING)
    private Role role; // role : USER, ADMIN

    @OneToMany(mappedBy = "user")
    private List<Board> boards; // 작성글

    @OneToMany(mappedBy = "user") 
    private List<Like> likes; // 유저가 누른 좋아요

}
