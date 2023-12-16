package backend.Gwelcome.repository;

import backend.Gwelcome.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends Payment> S save(S entity);

    @Query("SELECT p FROM Payment p WHERE p.orderId = :orderId")
    Payment findOrderId (@Param("orderId") String orderId);

    @Query("SELECT sum(p.Money) FROM Payment p")
    Long getTotalDonateAmount();

    @Query("SELECT sum(p.Money) FROM Payment p where p.member.id = :memberId")
    Long getMyTotalDonateAmount(@Param("memberId") String memberId);

    @Query("SELECT p FROM Payment p join fetch p.member m where p.member.id = :memberId")
    List<Payment> myDonate(@Param("memberId") String memberId);

}
