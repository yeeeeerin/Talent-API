package talent.com.auth.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    private String profileImg;

    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;


}
