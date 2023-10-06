package com.grampus.commnuity.controller;

import com.grampus.commnuity.config.auth.UserDetail;
import com.grampus.commnuity.dto.BoardDto;
import com.grampus.commnuity.dto.UserJoinDto;
import com.grampus.commnuity.service.BoardService;
import com.grampus.commnuity.service.UserService;
import com.grampus.commnuity.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final BoardService boardService;
    private final UserService userService;


    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "/users/join";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody UserJoinDto userJoinDto) {
        log.info(userJoinDto.toString());

        // 중복 검사
        if (userService.existsDuplicatedUser(userJoinDto)) {
            log.info("중복 존재");
            return new ResponseEntity<>("fail", HttpStatus.CONFLICT);
        }

        userService.join(userJoinDto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        // 로그인 성공 시, 이전 페이지로 이동하기 위해 이전 페이지 저장
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login") && !uri.contains("/join")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "/users/login";
    }

    @GetMapping("/boards")
    public String userBoardsPage(Model model,
                                 @RequestParam(required = false, defaultValue = "1") int page,
                                 Authentication auth) {

        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        Long userId = userDetail.getId();

        int totalBoard = boardService.getTotalMyBoardCount(userId);
        Pagination pagination = new Pagination(totalBoard, page);

        List<BoardDto> myBoardList = boardService.getMyBoardList(userId, pagination.getStartIdx(), pagination.getPageSize());

        model.addAttribute("boards", myBoardList);
        model.addAttribute("currentPage", pagination.getPage());
        model.addAttribute("totalPage", pagination.getTotalPage());
        return "/users/boards";
    }


    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity<Long> getCurrentUserId(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return new ResponseEntity<>(((UserDetail) auth.getPrincipal()).getId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }


}
