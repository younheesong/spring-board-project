package com.grampus.commnuity.controller;

import com.grampus.commnuity.dto.UserJoinDto;
import com.grampus.commnuity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "/users/join";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody UserJoinDto userJoinDto ){
        log.info(userJoinDto.toString());

        // 중복 검사
        if(userService.existsDuplicatedUser(userJoinDto)){
            log.info("중복 존재");
           return new ResponseEntity<>("fail", HttpStatus.CONFLICT);
        }

        userService.join(userJoinDto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request){
        // 로그인 성공 시, 이전 페이지로 이동하기 위해 이전 페이지 저장
        String uri = request.getHeader("Referer");
        if(uri!=null && !uri.contains("/login") && !uri.contains("/join")){
            request.getSession().setAttribute("prevPage", uri);
        }
        return "/users/login";
    }


}
