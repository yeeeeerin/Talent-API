package talent.com.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talent.com.auth.domain.Member;
import talent.com.auth.domain.MemberDetail;
import talent.com.auth.domain.MemberRole;
import talent.com.auth.dto.MemberDto;
import talent.com.auth.repository.MemberRepository;
import talent.com.auth.service.EmailService;
import talent.com.auth.service.FileUploadDownloadService;
import talent.com.auth.service.MemberService;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final FileUploadDownloadService fileUploadDownloadService;

    @Override
    @Transactional
    public String singUp(MemberDto.RegistMember registMember) {

        Member member = new Member();
        member.setEmail(registMember.getEmail());
        member.setPassword(passwordEncoder.encode(registMember.getPassword()));
        member.setUsername(registMember.getUsername());
        member.setMemberRole(MemberRole.NON_USER);
        member.setProfileImg(fileUploadDownloadService.storeFile(registMember.getProfileImg()));

        memberRepository.save(member);

        emailService.sendMessage(registMember.getEmail(), member.getId());

        return "create user";

    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto.MyInfo getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("Have no registered members"));
        return MemberDto.MyInfo.of(member);
    }


    @Override
    public void certification(Long memberId) {
        memberRepository.findById(memberId)
                .ifPresent(member -> member.setMemberRole(MemberRole.USER));
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Have no registered members"));
        return MemberDetail.from(member);
    }
}
