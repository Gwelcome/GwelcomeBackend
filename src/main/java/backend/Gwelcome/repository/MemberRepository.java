package backend.Gwelcome.repository;

import backend.Gwelcome.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
}