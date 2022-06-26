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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (member == null) {
            bindingResult.reject("loginFail", "아이디, 또는 비밀번호를 잘못 입력하셨습니다.");
            return "login/loginForm";
        }

        return "redirect:/";
    }
}
