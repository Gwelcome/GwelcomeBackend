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
    @Query("SELECT p FROM Policy p WHERE p.policy_field LIKE %:memberInterest1% OR p.policy_field LIKE %:memberInterest2% OR p.policy_field LIKE %:memberInterest3%")
    List<Policy> memberCustomizedPolicy(@Param("memberInterest1") String memberInterest1, @Param("memberInterest2") String memberInterest2,
                                        @Param("memberInterest3") String memberInterest3,Pageable pageable);
    List<Policy> findByLikesIn(List<Likes> likes);
}