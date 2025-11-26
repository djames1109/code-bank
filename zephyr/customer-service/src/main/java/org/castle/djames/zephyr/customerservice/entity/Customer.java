package org.castle.djames.zephyr.customerservice.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstName;
    public String lastName;
    public String nationalId;
    public LocalDate birthDate;
    public String address;
    public String email;
    public String phone;
    public KycStatus kycStatus;
    public String kycReferenceId;
    public Instant kycIssuedDate;
    public Instant kycExpiryDate;
    @CreationTimestamp
    public Instant createdDate;
    @UpdateTimestamp
    public Instant updatedDate;

    /**
     * Find a customer by national ID.
     */
    public static Optional<Customer> findByNationalId(String nationalId) {
        return find("nationalId", nationalId).firstResultOptional();
    }
}
