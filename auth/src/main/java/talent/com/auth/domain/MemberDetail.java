package talent.com.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MemberDetail implements UserDetails {
    private static final long serialVersionUID = -2905136520515064836L;
    private final List<SimpleGrantedAuthority> auhorities = new LinkedList<>();
    private final Member member;

    private MemberDetail(final Member member) {
        this.member = member;
        auhorities.add(new SimpleGrantedAuthority(member.getMemberRole().getRoleName()));
    }

    public static MemberDetail from(final Member member) {
        return new MemberDetail(member);
    }

    public static MemberDetail from(final String email, final String role) {
        final Member member = new Member();
        member.setEmail(email);
        member.setMemberRole(MemberRole.getMemberRole(role));
        return new MemberDetail(member);
    }

    public String getRole() {
        return auhorities.get(0).getAuthority();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auhorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
