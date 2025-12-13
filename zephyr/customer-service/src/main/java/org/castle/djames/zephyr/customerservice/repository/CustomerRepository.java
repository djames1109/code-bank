package org.castle.djames.zephyr.customerservice.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import org.castle.djames.zephyr.customerservice.entity.Customer;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Optional<Customer> findByNationalId(String nationalId) {
        return find("nationalId", nationalId).firstResultOptional();
    }
}
