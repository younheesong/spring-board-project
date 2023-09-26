package com.grampus.commnuity.repository;


import com.grampus.commnuity.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface UserRepository {
    /* 유저 저장 */
    void saveUser(User user);

    /* 유저 조회 */
    Optional<User> findByLoginId(String loginId);
}
