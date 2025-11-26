package org.castle.djames.zephyr.customerservice.service.biz.externalkyc;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record KycRegisterRequest(String firstName,
                                 String lastName,
                                 String nationalId,
                                 LocalDate birthDate,
                                 String email) {
}
