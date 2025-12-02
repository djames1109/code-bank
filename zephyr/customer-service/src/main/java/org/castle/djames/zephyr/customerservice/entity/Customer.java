package org.castle.djames.zephyr.customerservice.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String nationalId;
    private LocalDate birthDate;
    private String address;
    private String email;
    private String phone;
    private KycStatus kycStatus;
    private String kycReferenceId;
    private Instant kycIssuedDate;
    private Instant kycExpiryDate;
    @CreationTimestamp
    private Instant createdDate;
    @UpdateTimestamp
    private Instant updatedDate;


    public static Optional<Customer> findById(Long id) {
        return find("id", id).firstResultOptional();
    }

    public static Optional<Customer> findByNationalId(String nationalId) {
        return find("nationalId", nationalId).firstResultOptional();
    }
}
