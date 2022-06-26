package WebToyProject1.webService.web.controller;

import WebToyProject1.webService.domain.login.LoginService;
import WebToyProject1.webService.domain.member.Member;
import WebToyProject1.webService.web.form.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (member == null) {
            bindingResult.reject("loginFail", "아이디, 또는 비밀번호를 잘못 입력하셨습니다.");
            return "login/loginForm";
        }

        // 로그인에 성공하면 쿠키를 부여하고, 새로운 화면으로 넘어가게 해주자.
        Cookie cookie = new Cookie("memberId", String.valueOf(member.getId()));
        response.addCookie(cookie);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String memberId) {
        Cookie cookie = new Cookie(memberId, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}















