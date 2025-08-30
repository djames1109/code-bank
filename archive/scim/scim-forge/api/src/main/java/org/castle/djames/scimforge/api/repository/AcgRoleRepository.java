package org.castle.djames.scimforge.api.repository;

import org.castle.djames.scimforge.api.entity.AcgRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcgRoleRepository extends JpaRepository<AcgRole, Long> {
}
