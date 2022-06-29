package WebToyProject1.webService.web.argumentResolver;

import WebToyProject1.webService.domain.member.Member;
import WebToyProject1.webService.web.session.LoginConst;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean isMemberType = parameter.getParameterType().isAssignableFrom(Member.class);

        return hasLoginAnnotation && isMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // session이 할당되었는지 여부를 조사하여, 로그인 여부를 확인한다 -> 로그인 화면 페이지 송출
        HttpServletRequest request = (HttpServletRequest) webRequest;
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return session.getAttribute(LoginConst.LOGIN_MEMBER);
    }
}
