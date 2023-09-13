package com.grampus.commnuity.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {
    @Id @GeneratedValue
    @Column(name="file_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    private String originalFileName;
    private String savedFileName;
}
