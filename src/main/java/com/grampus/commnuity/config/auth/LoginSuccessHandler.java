package com.grampus.commnuity.config.auth;

import com.grampus.commnuity.domain.User;
import com.grampus.commnuity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(30*60);

        User loginUser = userRepository.findByLoginId(authentication.getName()).get();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if(prevPage !=null){
            writer.println("<script>alert('"+ loginUser.getUsername()+ "님 반갑습니다.'); location.href='"+prevPage+"';</script>");
        }else{
            writer.println("<script>alert('"+ loginUser.getUsername()+ "님 반갑습니다.'); location.href='/';</script>");
        }

    }
}
