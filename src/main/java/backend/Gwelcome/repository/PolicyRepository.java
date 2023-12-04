package backend.Gwelcome.repository;

import backend.Gwelcome.model.Likes;
import backend.Gwelcome.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    @Query("SELECT COUNT(*) FROM Policy p WHERE p.policy_field = :memberInterest")
    long countPoliciesByMember(@Param("memberInterest") String memberInterest);
    List<Policy> findByLikesIn(List<Likes> likes);
}