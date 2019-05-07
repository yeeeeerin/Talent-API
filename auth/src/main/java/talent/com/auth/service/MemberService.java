package talent.com.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import talent.com.auth.dto.MemberDto;

public interface MemberService extends UserDetailsService {

    String singUp(MemberDto.RegistMember registMember);

    MemberDto.MyInfo getMember(Long memberId);

    void certification(Long memberId);

}
