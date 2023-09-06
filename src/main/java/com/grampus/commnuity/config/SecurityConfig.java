package com.grampus.commnuity.config;


import com.grampus.commnuity.config.auth.LoginSuccessHandler;
import com.grampus.commnuity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    private final UserRepository userRepository;

    // 로그인하지 않은 유저들만 접근 가능한 URL
    private static final String[] anonymousUserUrl = {"/users/login", "/users/join"};

    // 로그인한 유저들만 접근 가능한 URL
    private static final String[] authenticatedUserUrl = {"/boards/**/**/edit", "/boards/**/**/delete", "/likes/**", "/users/myPage/**", "/users/edit", "/users/delete"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(anonymousUserUrl).anonymous()
                .antMatchers(authenticatedUserUrl).authenticated()
                .antMatchers("/users/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .and()
                // 폼 로그인
                .formLogin()
                .loginPage("/users/login")      // 로그인 페이지
                .usernameParameter("loginId")   // 로그인에 사용될 id
                .passwordParameter("password")  // 로그인에 사용될 password
                .failureUrl("/users/login?fail")         // 로그인 실패 시 redirect 될 URL => 실패 메세지 출력
                .successHandler(new LoginSuccessHandler(userRepository))
                .and()
                // 로그아웃
                .logout()
                .logoutUrl("/users/logout")     // 로그아웃 URL
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .build();
    }

}
