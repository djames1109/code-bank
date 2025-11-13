package org.castle.djames.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public class Customer extends PanacheEntity {

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

    public static Optional<Customer> findById(String id) {
        return findByIdOptional(id);
    }

    public static Optional<Customer> findByNationalId(String nationalId) {
        return find("nationalId", nationalId).firstResultOptional();
    }

    public static Optional<Customer> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
