package talent.com.auth.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum MemberRole implements GrantedAuthority {
    USER("AUTORIZED"), NON_USER("UNAUTORISED");

    private final String roleName;

    MemberRole(final String roleName) {
        this.roleName = roleName;
    }

    static MemberRole getMemberRole(final String role) {
        return MemberRole.getMemberRole(Stream.of(MemberRole.values())
                .map(MemberRole::getRoleName)
                .filter(s -> role.equals(s))
                .findFirst()
                .get());
    }

    public static Collection<? extends GrantedAuthority> parseAuthorities(final MemberRole role) {
        return Arrays.asList(role).stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getAuthority() {
        return "";
    }
}
