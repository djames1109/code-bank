package org.castle.djames.zephyr.customerservice.service;

import org.castle.djames.zephyr.customerservice.dto.CustomerDetailResponse;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.entity.Customer;
import org.castle.djames.zephyr.customerservice.entity.KycStatus;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycRegisterRequest;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycRegisterResponse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public abstract class BaseCommandService {

    protected KycRegisterRequest buildKycRegisterRequest(CustomerRequest customerRequest) {
        return KycRegisterRequest.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .nationalId(customerRequest.nationalId())
                .birthDate(customerRequest.birthDate())
                .email(customerRequest.email())
                .build();
    }

    protected CustomerDetailResponse buildCustomerDetailResponse(Customer customer) {
        return CustomerDetailResponse.builder()
                .id(customer.id)
                .firstName(customer.firstName)
                .lastName(customer.lastName)
                .email(customer.email)
                .phone(customer.phone)
                .kycStatus(customer.kycStatus)
                .kycIssuedDate(customer.kycIssuedDate)
                .kycExpiryDate(customer.kycExpiryDate)
                .createdDate(Instant.now())
                .updatedDate(Instant.now())
                .build();
    }

    protected Customer buildCustomer(CustomerRequest customerRequest, KycRegisterResponse kycRegisterResponse) {
        return Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .nationalId(customerRequest.nationalId())
                .birthDate(customerRequest.birthDate())
                .address(customerRequest.address())
                .email(customerRequest.email())
                .phone(customerRequest.phone())
                .kycStatus(KycStatus.valueOf(kycRegisterResponse.status().toUpperCase()))
                .kycReferenceId(kycRegisterResponse.referenceId())
                .kycIssuedDate(formatDate(kycRegisterResponse.details().documentIssued()))
                .kycExpiryDate(formatDate(kycRegisterResponse.details().documentExpiry()))
                .build();
    }

    protected Instant formatDate(String date) {
        final var DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, DATE_FORMATTER).atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
