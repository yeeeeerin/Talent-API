package talent.com.auth.controller;

import com.example.talent.dto.MemberDto;
import com.example.talent.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public String signUp(@RequestParam(value = "email") String email,
                         @RequestParam(value = "username") String name,
                         @RequestParam(value = "password") String password,
                         @RequestParam(value = "profile_img", required = false) MultipartFile file) {

        MemberDto.RegistMember registMember = MemberDto.RegistMember.of(email, name, password, file);
        memberService.singUp(registMember);
        return "ok";
    }

    @GetMapping("/{memberId}/get")
    public MemberDto.MyInfo getMember(@PathVariable("memberId") Long memberId) {
        return memberService.getMember(memberId);

    }

    @GetMapping("/{memberId}")
    public String memberCertification(@PathVariable("memberId") Long memberId) {
        memberService.certification(memberId);
        return "인증에 성공하셨습니다";
    }


}
