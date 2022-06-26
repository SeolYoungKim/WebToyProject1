package WebToyProject1.webService.web.session;

import WebToyProject1.webService.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        // 가짜(Mock) 응답 객체와 요청 객체 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // 넘겨줄 Member 생성
        Member member = new Member();

        // 세션 생성 및 request에 response에서 받은 쿠키 전달
        sessionManager.createSession(member, response);
        request.setCookies(response.getCookies());

        // 세션 조회
        Object sessionObject = sessionManager.getSession(request);

        // 세션을 조회하여 얻은 객체와, 내가 넣어준 객체가 같은지 확인.
        assertThat(sessionObject).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);

        // 세션 저장소 조회 시, 세션이 없는지 확인
        assertThat(sessionManager.getSession(request)).isNull();

    }

}