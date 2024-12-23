package org.castle.djames.scimforge.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String status;

    private boolean isAdmin;

    private String userType;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AcgRoleUserMapping> acgRoleUserMapping;

}
