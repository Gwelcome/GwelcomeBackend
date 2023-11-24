package backend.Gwelcome.repository;

import backend.Gwelcome.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}