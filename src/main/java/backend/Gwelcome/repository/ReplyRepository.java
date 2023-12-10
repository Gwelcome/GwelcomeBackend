package backend.Gwelcome.repository;

import backend.Gwelcome.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r join fetch r.policy p where r.policy.id = :policyId")
    List<Reply> replyPolicy(@Param("policyId") Long policyId);
    @Query("SELECT COUNT(*) FROM Reply r WHERE r.policy.id = :policyId")
    int countRepliesByPolicy(@Param("policyId") Long policyId);
    @Query("SELECT COUNT(*) FROM Reply r WHERE r.member.id = :memberId")
    long countRepliesByMember(@Param("memberId") String memberId);
}