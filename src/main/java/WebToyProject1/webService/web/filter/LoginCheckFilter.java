package WebToyProject1.webService.web.filter;

import WebToyProject1.webService.web.session.LoginConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/login", "/logout","/members/add", "/css/*"};  // 해당 URI는 아무나 사용 가능.

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /**
         * 로그인 인증 사용자만 이용할 수 있게 바꾸어보자!
         */
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession();

        log.info("로그인 인증 필터 입니다.");

        try {
            if (isLoginCheckPath(requestURI)) {  // 인증이 필요한 URI가 진입하는 로직.
                log.info("whiteList에 없는 URI는 인증 사용자만 접근할 수 있습니다.");

                // 미인증 사용자 처리
                if (session == null || session.getAttribute(LoginConst.LOGIN_MEMBER) == null) {
                    log.info("세션이 없습니다. 미인증 사용자입니다.");
                    httpResponse.sendRedirect("/login?redirectTo=" + requestURI);
                    return;  // 미인증 사용자의 경우, 다음 필터고 뭐고 더이상 진행 X
                }

                // 인증 되면 아래 로직 수행
                log.info("인증 사용자입니다. 다음으로 진행합니다.");
            }

            chain.doFilter(httpRequest, httpResponse);
            // 여기서, 화면이 두개 출력되는 기현상을 발생시켰다. doFilter를 두개나 써가지고...ㅋㅋㅋㅋㅋㅋㅋ,,,


        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 필터가 종료됩니다.");
        }

    }

    // URI check method -> 이부분은 기억이 안나서 배낌 ㅠ.ㅠ
    public boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
