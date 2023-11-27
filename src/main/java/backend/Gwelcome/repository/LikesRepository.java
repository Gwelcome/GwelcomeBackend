package backend.Gwelcome.repository;

import backend.Gwelcome.model.Likes;
import backend.Gwelcome.model.Member;
import backend.Gwelcome.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByPolicyAndMember(Policy policy, Member member);

    List<Likes> findByMember(Member member);
}