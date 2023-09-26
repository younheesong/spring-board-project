package com.grampus.commnuity.domain;


import lombok.*;


@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {
    private Long id;

    private Long boardId;

    private String originalFileName;

    private String savedFileName;
}
