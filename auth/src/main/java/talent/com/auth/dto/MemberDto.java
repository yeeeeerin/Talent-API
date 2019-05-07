package talent.com.auth.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;
import talent.com.auth.domain.Member;


public class MemberDto {

    @Value
    public static class RegistMember {
        private String email;
        private String username;
        private String password;
        private MultipartFile profileImg;

        public static RegistMember of(String email, String username, String password, MultipartFile multipartFile) {
            return new RegistMember(email, username, password, multipartFile);
        }
    }

    @Value
    public static class MyInfo {
        private Long memberId;
        private String email;
        private String username;
        private String profileImg;

        public static MyInfo of(Member member) {
            return new MyInfo(member.getId(), member.getEmail(), member.getUsername(), member.getProfileImg());
        }
    }


}
