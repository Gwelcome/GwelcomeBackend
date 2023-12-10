package backend.Gwelcome.repository;

import backend.Gwelcome.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query("SELECT p FROM Payment p WHERE p.orderId = :orderId")
    Payment findOrderId (@Param("orderId") String orderId);

}
