package backend.Gwelcome.repository;

import backend.Gwelcome.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT COUNT(*) FROM Reply r WHERE r.member.id = :memberId")
    long countRepliesByMember(@Param("memberId") String memberId);
}