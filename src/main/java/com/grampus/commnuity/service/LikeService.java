package com.grampus.commnuity.service;

import com.grampus.commnuity.domain.Like;
import com.grampus.commnuity.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardService boardService;

    // 좋아요 누르기
    @Transactional
    public void addLike(Long userId, Long boardId){
        /* board.viewsCount 증가 */
        boardService.increaseLikesCount(boardId);

        /* like save */
        likeRepository.saveLike(Like.builder().userId(userId).boardId(boardId).build());
    }

    // 좋아요 삭제하기
    @Transactional
    public void deleteLike(Long loginId, Long boardId){
        /* board.viewsCount 감소 */
        boardService.decreaseLikesCount(boardId);

        /* like delete */
        likeRepository.deleteLike(loginId, boardId);
    }

    // 좋아요 눌렀는지 확인
    public boolean isLiked(Long userId, Long boardId){
        return likeRepository.getLikeByUserLoginIdAndBoardId(userId, boardId).isPresent();
    }
}
