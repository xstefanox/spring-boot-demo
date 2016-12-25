package xstefanox.entity;

import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c")
    Stream<Customer> streamAll();
}
