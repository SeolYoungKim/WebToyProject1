package WebToyProject1.webService.web.controller;

import WebToyProject1.webService.domain.member.Member;
import WebToyProject1.webService.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String loginHome(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "home";
        }

        Member findMember = memberRepository.findById(memberId);
        if (findMember == null) {
            return "home";
        }

        model.addAttribute("member", findMember);
        return "loginHome";
    }
}
