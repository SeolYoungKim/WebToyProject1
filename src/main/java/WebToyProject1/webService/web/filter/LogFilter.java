package WebToyProject1.webService.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    /**
     * 각 요청에 대한 로그를 찍는 필터를 만들어보자!
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);

            // 중요. 다음 필터로 진행시켜주거나, 서블릿으로 진행시켜준다. 없으면 먹통됨.
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e;
        } finally {
            // 다음 필터의 로직이 끝나거나, 서블릿->컨트롤러의 로직이 끝나면 이 로직 실행
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }
}
