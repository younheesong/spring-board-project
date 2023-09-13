package com.grampus.commnuity.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "likes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id @GeneratedValue
    @Column(name="like_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

}
