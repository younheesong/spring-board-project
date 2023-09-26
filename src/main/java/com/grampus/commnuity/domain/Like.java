package com.grampus.commnuity.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Table(name = "likes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private Long id;

    private Long userId;

    private Long boardId;

}
