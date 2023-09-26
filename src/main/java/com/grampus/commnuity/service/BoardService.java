package com.grampus.commnuity.service;


import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Category;
import com.grampus.commnuity.domain.File;
import com.grampus.commnuity.domain.User;
import com.grampus.commnuity.dto.BoardCreateDto;
import com.grampus.commnuity.dto.BoardDto;
import com.grampus.commnuity.dto.BoardEditDto;

import com.grampus.commnuity.repository.BoardRepository;
import com.grampus.commnuity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FileService fileService;


    // 카테고리별 페이지 조회
    public List<BoardDto> getBoardList(Category category, String keyword, int start, int pageSize) {
        return boardRepository.getBoardList(category, keyword, start, pageSize);
    }


    public int getTotalBoardCount(Category category, String keyword) {
        return boardRepository.getTotalBoardCount(category, keyword);
    }

    // 특정 글 조회
    public BoardDto getBoard(Long boardId) {
        BoardDto board = boardRepository.getBoardItemById(boardId);
        log.info("board: " + board);

        return board;
    }

    // 글 작성
    @Transactional
    public Long writeBoard(BoardCreateDto boardCreateDto, String loginId) {
        User user = userRepository.findByLoginId(loginId).get();
        log.info("user id : " + user.getId());

        // content 내 이미지 태그에서 저장된 파일 이름 추출
        ArrayList<String> contentImageNames = getSavedContentImageNamesFromHtml(boardCreateDto.getContent());
        if (!contentImageNames.isEmpty()) {
            boardCreateDto.setContentImages(contentImageNames.toString());
        }


        // board 저장
        Board savedBoard = boardCreateDto.toEntity(user.getId());
        boardRepository.saveBoard(savedBoard);

        // 첨부파일 있으면, 파일 repo에 따로 저장
        if (!boardCreateDto.getFiles().isEmpty()) {
            List<MultipartFile> files = boardCreateDto.getFiles();
            fileService.saveFiles(files, savedBoard.getId());
        }
        return savedBoard.getId();
    }

    // 글 수정
    @Transactional
    public Long editBoard(Long boardId, BoardEditDto boardEditDto) throws IOException {
        BoardDto board = boardRepository.getBoardItemById(boardId);
        if (board == null) {
            return null;
        }

        ArrayList<String> newContentImageNames = getSavedContentImageNamesFromHtml(boardEditDto.getContent());
        if (!newContentImageNames.isEmpty()) {
            boardEditDto.setContentImages(newContentImageNames.toString());
        }

        /* 에디터의 기존 이미지에서 삭제된 이미지를 저장소에서 삭제 */
        if (board.getContentImages() != null) {
            log.info("old content images: " + board.getContentImages());
            String[] oldContentImagesNames = board.getContentImages()
                    .replace("[", "")
                    .replace("]", "")
                    .replaceAll(" ","")
                    .split(",");

            Arrays.stream(oldContentImagesNames)
                    .filter(oldContentImage -> !newContentImageNames.contains(oldContentImage))
                    .forEach((removedImage) -> {
                        log.info("removed Image: " + removedImage);
                        fileService.deleteFile(removedImage);
                    });
        }

        boardEditDto.setId(boardId);
        boardRepository.updateBoard(boardEditDto);


        /* 게시글 첨부파일 이미지 처리 로직 */
        List<File> files = fileService.getFiles(boardId);

        /* 이전 이미지를 삭제한 경우 해당 이미지 삭제 */
        if (!files.isEmpty()) {
            for (File oldFile : files) {
                if (boardEditDto.getOldFiles() == null || !boardEditDto.getOldFiles().contains(oldFile.getId())) {
                    log.info("oldFile delete: " + oldFile.getId());
                    fileService.deleteFile(oldFile);
                }
            }
        }

        /* 추가된 이미지가 있을 경우 이미지 추가 */
        if (!boardEditDto.getNewFiles().isEmpty()) {
            List<MultipartFile> newFiles = boardEditDto.getNewFiles();
            fileService.saveFiles(newFiles, boardId);
        }

        return boardId;
    }

    // 글 삭제
    public Long deleteBoard(Long boardId) throws IOException {
        BoardDto board = boardRepository.getBoardItemById(boardId);

        if (board == null) {
            return null;
        }
        // 이미지 삭제 처리
        List<File> files = fileService.getFiles(boardId);
        if (files != null) {
            fileService.deleteFiles(files);
        }
        boardRepository.deleteBoard(boardId);
        return boardId;
    }

    // 자신이 작성한 글 갯수 조회
    public int getTotalMyBoardCount(Long userId) {
        return boardRepository.getTotalMyBoardCount(userId);
    }

    // 자신이 작성한 글 조회
    public List<BoardDto> getMyBoardList(Long userId, int start, int pageSize) {
        return boardRepository.getMyBoardList(userId, start, pageSize);
    }

    // 조회수 업데이트
    @Transactional
    public int updateViewsCount(Long boardId) {
        log.info("update view ");
        log.info("boardId : " + boardId);
        return boardRepository.updateViewsCount(boardId);
    }

    // 좋아요 증가 업데이트
    @Transactional
    public void increaseLikesCount(Long boardId) {
        log.info("add like :" + boardId);
        boardRepository.increaseLikesCount(boardId);
    }

    // 좋아요 감소 업데이트
    @Transactional
    public void decreaseLikesCount(Long boardId) {
        log.info("decrease like :" + boardId);
        boardRepository.decreaseLikesCount(boardId);
    }

    private ArrayList<String> getSavedContentImageNamesFromHtml(String html) {
        ArrayList<String> newContentImages = new ArrayList<>();
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출 정규표현식
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            newContentImages.add(matcher.group(1).split("/images/")[1]);
        }
        return newContentImages;
    }

}
