package com.grampus.commnuity.service;

import com.grampus.commnuity.domain.Board;
import com.grampus.commnuity.domain.Like;
import com.grampus.commnuity.domain.User;
import com.grampus.commnuity.dto.BoardDto;
import com.grampus.commnuity.repository.BoardRepository;
import com.grampus.commnuity.repository.LikeRepository;
import com.grampus.commnuity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 좋아요 누르기
    @Transactional
    public void addLike(String loginId, Long boardId){
        Board board = boardRepository.findById(boardId).get();
        User user = userRepository.findByLoginId(loginId).get();

        board.changeLikesCount(board.getLikesCount()+1);

        likeRepository.save(Like.builder().user(user).board(board).build());
    }

    // 좋아요 삭제하기
    @Transactional
    public void deleteLike(String loginId, Long boardId){
        Board board = boardRepository.findById(boardId).get();

        board.changeLikesCount(board.getLikesCount()-1);

        likeRepository.deleteByUserLoginIdAndBoardId(loginId, boardId);
    }

    // 좋아요 눌렀는지 확인
    public boolean isLiked(String loginId, Long boardId){
        return likeRepository.existsByUserLoginIdAndBoardId(loginId,boardId);
    }
}
