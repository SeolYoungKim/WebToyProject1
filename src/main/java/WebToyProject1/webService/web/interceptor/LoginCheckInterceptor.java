package WebToyProject1.webService.web.interceptor;

import WebToyProject1.webService.web.session.LoginConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        log.info("로그인 인증 요청 실행: {}", requestURI);

        if (session == null || session.getAttribute(LoginConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청: {}", requestURI);
            response.sendRedirect("/login?redirectTo=" + requestURI);

            return false;
        }

        return true;
    }
}
