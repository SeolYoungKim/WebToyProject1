package WebToyProject1.webService.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    private final String SESSION_COOKIE_NAME = "mySessionId";

    /**
     * 세션 생성
     * sessionId는 UUID를 사용할 것임.
     * sessionId=Member 형태로 Map에 저장할 것임.
     * 쿠키는 SESSION_COOKIE_NAME=sessionId의 형태로 다룰 것임.
     * response 객체에 쿠키를 담아서 보내주어야 함.
     */
    public void createSession(Object value, HttpServletResponse response) {
        
        String sessionId = UUID.randomUUID().toString();

        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    /**
     * 세션 조회
     * 세션 조회는 쿠키 저장소, 즉 클라이언트에서 조회를 하는 것임.
     * 요청할 때, 헤더에 쿠키 정보(SESSION_COOKIE_NAME=sessionId)를 담아서 넘겨줄 것임.
     * 이를 토대로 조회를 해야 함.
     * 조회를 하면, 클라이언트는 클라이언트에 해당하는 값을 얻어야 하므로, Object를 반환할 것임.
     */
    public Object getSession(HttpServletRequest request) {  // 굳이 파라미터로 cookieName을 받아 줄 필요가 없다.
//        Cookie의 name = SESSION_COOKIE_NAME
//        Cookie의 value = sessionId
        
//        Session의 key = sessionId
//        Session의 value = member
        
        // request가 가진 쿠키 중에 mySessionId라는 이름의 쿠키 조회
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);

        if (cookie == null) {  // 쿠키가 없으면 null 반환.
            return null;
        }

        // 쿠키가 있으면 쿠키의 값을 key로 해서, sessionStore에서 member 조회.
        return sessionStore.get(cookie.getValue());

    }

    /**
     * 세션 만료
     * 특정 세션을 만료시키고자 함.
     */
    public void expire(HttpServletRequest request) {
        // request가 가진 쿠키 중에 mySessionId라는 이름의 쿠키 조회
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);

        if (cookie != null) {
            sessionStore.remove(cookie.getValue());
        }

    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        // 놓친 부분. null일 경우를 처리해줘야 한다.
        if (request.getCookies() == null) {
            return null;
        }

        // request 객체 안의 쿠키가 여러개일 수 있기 때문에, 특정 cookieName을 갖는 쿠키를 따로 찾아주어야 한다.
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

}












