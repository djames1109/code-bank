package org.castle.djames.scimforge.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class AcgRole {

    @Id
    private Long id;

    private String appId;

    private String appName;

    private String accessGroup;

    private String accessGroupRole;

    private String parentAccessGroup;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "acg_role_id")
    private List<AcgRoleUserMapping> acgRoleUserMapping;

}
