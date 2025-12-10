package org.castle.djames.zephyr.customerservice.dto;

import java.time.Instant;
import lombok.Builder;
import org.castle.djames.zephyr.customerservice.entity.KycStatus;

@Builder
public record CustomerDetailResponse(Long id,
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
