package com.grampus.commnuity.controller;


import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import com.grampus.commnuity.dto.BoardCreateDto;
import com.grampus.commnuity.dto.BoardDto;
import com.grampus.commnuity.service.BoardService;
import com.grampus.commnuity.service.FileService;
import com.grampus.commnuity.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final LikeService likeService;

    // 게시글 조회(페이징)
    @GetMapping("/{category}")
    public String getBoardListPage(@PathVariable String category,
                               Model model,
                               @RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false) String keyword) {
        log.info("category: " + category);
        log.info("page: " + page);
        log.info("keyword: " + keyword);

        Category boardCategory = Category.of(category);
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("creationDate").descending());


        model.addAttribute("category", boardCategory);
        model.addAttribute("boards", boardService.getBoardList(boardCategory, pageRequest, keyword).toList());
        model.addAttribute("currentPage", boardService.getBoardList(boardCategory, pageRequest, keyword).getNumber());
        model.addAttribute("totalPage", boardService.getBoardList(boardCategory, pageRequest, keyword).getTotalPages());


        return "boards/list";
    }

    // 상세 게시글 조회
    @GetMapping("/{category}/{boardId}")
    public String getBoardDetailPage(@PathVariable String category, @PathVariable Long boardId, Model model, Authentication auth, @CookieValue(value="boardView", required = false) Cookie cookie, HttpServletResponse response){
        log.info("category: "+ category);
        log.info("boardId: "+ boardId);

        BoardDto boardDto = boardService.getBoard(boardId);
        if(boardDto==null){
            return "redirect:/boards/"+category;
        }

        model.addAttribute("board", boardDto);

        if(auth!=null){
            boolean liked = likeService.isLiked(auth.getName(), boardId);
            model.addAttribute("boardLiked", liked);
        }

        /* 조회수 로직 */
        Cookie oldCookie = cookie;

        if(oldCookie !=null){
            if(!oldCookie.getValue().contains("["+boardId+"]")){
                boardService.updateViewsCount(boardId);
                oldCookie.setValue(oldCookie.getValue()+"_["+boardId+"]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60*60*24); // 1일
                response.addCookie(oldCookie);
            }
        }else{
            boardService.updateViewsCount(boardId);
            Cookie newCookie = new Cookie("boardView", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60*60*24);
            response.addCookie(newCookie);
        }

        return "boards/detail";
    }

    // 게시글 생성 페이지
    @GetMapping("/write")
    public String writeBoardPage(Model model){
        model.addAttribute("type", "write");
        return "boards/write";
    }

    // 게시글 생성
    @PostMapping("/write")
    public String createBoard(BoardCreateDto boardCreateDto, Authentication auth){
        log.info("boardCreateDto.category: "+boardCreateDto.getCategory());
        log.info("boardCreateDto.title: "+boardCreateDto.getTitle());
        log.info("boardCreateDto.content: "+boardCreateDto.getContent());
        log.info("auth.name: "+auth.getName());

        boardService.writeBoard(boardCreateDto, auth.getName());

        return "redirect:/boards/java";
    }

    @GetMapping("/{category}/{boardId}/edit")
    public String editBoardPage(@PathVariable Long boardId, Model model){
        BoardDto boardDto = boardService.getBoard(boardId);
        model.addAttribute("type", "edit");
        model.addAttribute("category", boardDto.getCategory());
        model.addAttribute("title", boardDto.getTitle());
        model.addAttribute("content", boardDto.getContent());
        model.addAttribute("files", boardDto.getFiles());
        return "boards/write";
    }

    // 게시글 수정
    @PostMapping("/{category}/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId, @PathVariable String category, BoardDto boardDto) throws IOException {
        log.info("boardId: "+ boardId);
        log.info("boardDto: "+boardDto);

        boardService.editBoard(boardId, boardDto);
        return "redirect:/boards/"+category+"/"+boardId;
    }

    // 게시글 삭제
    @GetMapping ("/{category}/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId, @PathVariable Category category){
        log.info("boardId: "+boardId);

        boardService.deleteBoard(boardId);

        return "redirect:/boards/"+category;

    }




}
