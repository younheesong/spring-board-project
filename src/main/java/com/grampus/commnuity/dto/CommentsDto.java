package com.grampus.commnuity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.stream.Stream;

@Data
public class CommentsDto {
    public int totalPage;
    public int currentPage;
    public ArrayList comments;

}
