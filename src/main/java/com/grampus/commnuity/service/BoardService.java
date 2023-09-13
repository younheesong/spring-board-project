package com.grampus.commnuity.service;


import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import com.grampus.commnuity.domain.File;
import com.grampus.commnuity.domain.User;
import com.grampus.commnuity.dto.BoardCreateDto;
import com.grampus.commnuity.dto.BoardDto;
import com.grampus.commnuity.repository.BoardRepository;
import com.grampus.commnuity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    // 카테고리별 페이지 조회
    public Page<Board> getBoardList(Category category, PageRequest pageRequest, String keyword) {
        if (keyword != null) {
            return boardRepository.findAllByCategoryAndTitleContains(category, keyword, pageRequest);
        }
        return boardRepository.findAllByCategory(category, pageRequest);
    }

    // 특정 글 조회
    public BoardDto getBoard(Long boardId){
        Optional<Board> otpBoard = boardRepository.findById(boardId);
        if(otpBoard.isEmpty()){
            return null;
        }
        return BoardDto.of(otpBoard.get());
    }

    // 글 작성
    @Transactional
    public Long writeBoard(BoardCreateDto boardCreateDto, String loginId){
        User user = userRepository.findByLoginId(loginId).get();
        // board 저장
        Board savedBoard = boardRepository.save(boardCreateDto.toEntity(user));
        // 파일 있으면, 저장
        if(!boardCreateDto.getFiles().isEmpty()){
            List<MultipartFile> files = boardCreateDto.getFiles();
            List<File> savedFiles = fileService.saveFiles(files, savedBoard);
            savedBoard.setFiles(savedFiles);
        }
        return savedBoard.getId();
    }

   // 글 수정
   @Transactional
    public Long editBoard(Long boardId, BoardDto boardDto) throws IOException {
       Optional<Board> optBoard = boardRepository.findById(boardId);
       if(optBoard.isEmpty()){
           return null;
       }

       Board board = optBoard.get();
       log.info("board : "+ board);
       board.update(boardDto);

       // 게시글 이미지 처리

       // 이전 이미지를 삭제한 경우 해당 이미지 삭제
       if(!board.getFiles().isEmpty()) {
           for (File oldFile : board.getFiles()) {
               if(boardDto.getOldFiles()==null){
                   fileService.deleteFile(oldFile);
               } else if (!boardDto.getOldFiles().contains(oldFile.getId())) {
                   fileService.deleteFile(oldFile);
               }
           }
       }

       // 추가된 이미지가 있을 경우 이미지 추가
       if(!boardDto.getNewFiles().isEmpty()){
           List<MultipartFile> newFiles = boardDto.getNewFiles();
           List<File> savedFiles = fileService.saveFiles(newFiles, board);
           board.getFiles().addAll(savedFiles);
       }

       return boardId;
   }

    // 글 삭제
    public Long deleteBoard(Long boardId){
        Optional<Board> optBoard = boardRepository.findById(boardId);

        if(optBoard.isEmpty()){
            return null;
        }
        // 이미지 삭제 처리

        boardRepository.deleteById(boardId);
        return boardId;
    }

    // 자신이 작성한 글 조회
    public List<Board> getMyBoardList(String loginId){
        return boardRepository.findAllByUserLoginId(loginId);
    }


    // 조회수 업데이트
    @Transactional
    public int updateViewsCount(Long boardId){
        log.info("update view ");
        log.info("boardId : " + boardId);
        return boardRepository.updateViewsCount(boardId);
    }


}
