package com.turi.turi0411.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;


@Slf4j
public class SessionAuthenticationFilter extends OncePerRequestFilter {
    private static final String SESSION_ID = "loginUser";
    private static final String[] whitelist = {"/", "/test", "/user/signup", "/user/login", "/user/logout", "/css/*", "/*.ico"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String requestUri = request.getRequestURI();

        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            if(name.equals("cookie")) {
                String sessionValue = request.getHeader(name);
                log.info("첫번째: " + sessionValue);
                sessionValue = sessionValue.substring(8);
                log.info("두번째: " + sessionValue);
                String[] val = sessionValue.split(";");
                sessionValue = val[0].substring(0, val.length - 1);
                log.info("세번째: " + sessionValue);

                break;
            }
        }


        if(session != null && session.getAttribute(SESSION_ID) != null) {
            log.info("세션값 검증하기 위해 확인좀; "+session.getAttribute(SESSION_ID));
        }

        if(!PatternMatchUtils.simpleMatch(whitelist, requestUri)) {
            if(session == null || session.getAttribute(SESSION_ID) == null) {
                log.info("SessionFilter임, 인증 안 된 사용자의 요청임 {} ", request.getRequestURI());


                //response.sendRedirect("/user/login?redirectURL=" + request.getRequestURI());
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "세션필터임 ㅇㅈ안된 사용자 요청");

                SecurityContextHolder.clearContext();

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
