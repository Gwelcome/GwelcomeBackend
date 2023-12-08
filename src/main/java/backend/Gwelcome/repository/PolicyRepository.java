package backend.Gwelcome.repository;

import backend.Gwelcome.model.Likes;
import backend.Gwelcome.model.Policy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    @Query("SELECT COUNT(*) FROM Policy p WHERE p.policy_field like %:memberInterest%")
    long countPoliciesByMember(@Param("memberInterest") String memberInterest);
    @Query("SELECT p FROM Policy p WHERE p.policy_field like %:memberInterest%")
    List<Policy> memberCustomizedPolicy(@Param("memberInterest") String memberInterest, Pageable pageable);
    List<Policy> findByLikesIn(List<Likes> likes);
}