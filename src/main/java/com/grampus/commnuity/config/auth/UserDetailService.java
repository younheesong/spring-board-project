package com.grampus.commnuity.config.auth;

import com.grampus.commnuity.domain.User;
import com.grampus.commnuity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    // db에서 회원정보를 가져오는 역할.
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId).orElseThrow(()-> {
            return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        });
        return new UserDetail(user);
    }
}
