package com.grampus.commnuity.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @NotNull
    private String loginId; // 로그인 시 사용하는 아이디

    @NotNull
    private String password; // 비밀번호

    @NotNull
    private String username; // 닉네임

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role; // role : USER, ADMIN

    @OneToMany(mappedBy = "user")
    private List<Board> boards; // 작성글

    @OneToMany(mappedBy = "user") 
    private List<Like> likes; // 유저가 누른 좋아요

}
