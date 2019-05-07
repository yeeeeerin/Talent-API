package talent.com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talent.com.auth.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
}
