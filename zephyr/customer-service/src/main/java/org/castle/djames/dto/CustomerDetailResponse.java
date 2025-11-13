package org.castle.djames.dto;

import lombok.Builder;
import org.castle.djames.entity.KycStatus;

import java.time.Instant;

@Builder
public record CustomerDetailResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        KycStatus kycStatus,
        Instant kycIssuedDate,
        Instant kycExpiryDate,
        Instant createdDate,
        Instant updatedDate) {
}
