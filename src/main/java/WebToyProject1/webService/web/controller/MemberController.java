package WebToyProject1.webService.web.controller;

import WebToyProject1.webService.domain.member.Member;
import WebToyProject1.webService.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") Member member) {
        return "/members/addMemberForm";
    }

    @PostMapping("/add")
    public String addMember(
            @Validated @ModelAttribute Member member,
            BindingResult bindingResult
            ) {

        if (bindingResult.hasErrors()) {
            log.info("회원가입 검증이 실패했습니다. errors={}", bindingResult);
            return "/members/addMemberForm";
        }

        log.info("회원 가입 완료. 회원 loginID={}", member.getLoginId());
        memberRepository.save(member);
        return "redirect:/";  // 왜 굳이 redirect로 홈을 보낼까 ?

    }
}
