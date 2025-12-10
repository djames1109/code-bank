package org.castle.djames.zephyr.customerservice.service.biz.externalkyc;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record KycRegisterRequest(String firstName,
                                 String lastName,
                                 String nationalId,
                                 LocalDate birthDate,
                                 String email) {
}
