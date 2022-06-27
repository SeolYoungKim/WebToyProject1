package WebToyProject1.webService.web.controller;

import WebToyProject1.webService.domain.member.Member;
import WebToyProject1.webService.domain.repository.MemberRepository;
import WebToyProject1.webService.web.session.LoginConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping
    public String home() {
        log.info("Home 정상 진입");
        return "home";
    }

    @GetMapping
    public String loginHome(  // 얘는 세션에 왜 접근을 하느냐? 로그인 사용자에게 화면 따로 띄워주려고 그런다..!
            @SessionAttribute(name = LoginConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
