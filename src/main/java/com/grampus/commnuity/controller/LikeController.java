package com.grampus.commnuity.controller;

import com.grampus.commnuity.config.auth.UserDetail;
import com.grampus.commnuity.service.BoardService;
import com.grampus.commnuity.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping ("/{boardId}/add")
    @ResponseStatus(HttpStatus.OK)
    public String addLike(@PathVariable Long boardId, Authentication auth){
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        Long userId = userDetail.getId();

        likeService.addLike(userId, boardId);
        return "ok";
    }

    @PostMapping("/{boardId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteLike(@PathVariable Long boardId, Authentication auth) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        Long userId = userDetail.getId();

        likeService.deleteLike(userId, boardId);
        return "ok";
    }

}
