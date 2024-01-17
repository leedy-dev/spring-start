package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    /**
     * DI (Dependency Injection) 방법
     * 1. 필드 주입 (비추) - 변경이 어려움
     * 2. setter 주입 - 생성 이후 setter를 통해 주입됨. public으로 노출되므로 변경 위험이 있음.
     * 3. 생성자 주입 (권장)
     * 스프링 실행될 때 생성자를 통해 주입되고 변경 되지 않음.
     * 의존관계가 실행 중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입 권장
     */

    /* 1. 필드 주입
    @Autowired
    private MemberService memberService;
    */
    
    /* 2. setter 주입
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    */

    // 3. 생성자 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
