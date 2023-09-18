package com.grampus.commnuity.service;

import com.grampus.commnuity.dto.UserJoinDto;
import com.grampus.commnuity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void join(UserJoinDto userJoinDto){
        String encodedPassword = encoder.encode(userJoinDto.getPassword());
        userRepository.save(userJoinDto.toEntity(encodedPassword));
    }

    public boolean existsDuplicatedUser(UserJoinDto userJoinDto){
        // 아이디 중복 체크
        if(userRepository.existsByLoginId(userJoinDto.getLoginId())){
            return true;
        }
        return false;
    }
}
