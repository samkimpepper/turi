package com.turi.turi0411.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
public class SessionAuthenticationFilter extends OncePerRequestFilter {
    private static final String SESSION_ID = "loginUser";
    private static final String[] whitelist = {"/", "/user/signup", "/user/login", "/user/logout", "/css/*", "/*.ico"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String requestUri = request.getRequestURI();

        if(!PatternMatchUtils.simpleMatch(whitelist, requestUri)) {
            if(session == null || session.getAttribute(SESSION_ID) == null) {
                log.info("SessionFilter임, 인증 안 된 사용자의 요청임 {} ", request.getRequestURI());

                response.sendRedirect("/user/login?redirectURL=" + request.getRequestURI());

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
