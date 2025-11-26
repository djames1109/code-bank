package org.castle.djames.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.castle.djames.dto.CustomerDetailResponse;
import org.castle.djames.dto.CustomerRequest;
import org.castle.djames.entity.KycStatus;
import org.castle.djames.service.biz.externalkyc.KycRegisterRequest;
import org.castle.djames.service.biz.externalkyc.KycRegisterResponse;
import org.castle.djames.service.biz.externalkyc.KycService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class CommandService {

    private final KycService kycService;

    public CommandService(@RestClient KycService kycService) {
        this.kycService = kycService;
    }

    /**
     * Onboards a new customer by performing the following actions:
     * 1. Builds a KYC registration request from the provided customer details.
     * 2. Registers the customer using an external KYC service.
     * 3. Constructs and returns a detailed customer response.
     *
     * @param customerRequest the details of the customer to be onboarded, including first name, last name,
     *                        national ID, date of birth, email, phone number, and address.
     * @return a {@code CustomerDetailResponse} containing detailed information about the customer,
     * including KYC status, issued date, expiry date, and created/updated timestamps.
     */
    public CustomerDetailResponse onboardCustomer(CustomerRequest customerRequest) {
        var registerRequest = buildKycRegisterRequest(customerRequest);
        var response = kycService.register(registerRequest);

        return buildCustomerDetailResponse(customerRequest, response);
    }

//    ====================== Helper methods ================================

    private KycRegisterRequest buildKycRegisterRequest(CustomerRequest customerRequest) {
        return KycRegisterRequest.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .nationalId(customerRequest.nationalId())
                .birthDate(customerRequest.birthDate())
                .email(customerRequest.email())
                .build();
    }

    private CustomerDetailResponse buildCustomerDetailResponse(CustomerRequest customerRequest, KycRegisterResponse response) {
        return CustomerDetailResponse.builder()
                .id(1L)
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .phone(customerRequest.phone())
                .kycStatus(KycStatus.valueOf(response.status().toUpperCase()))
                .kycIssuedDate(formatDate(response.details().documentIssued()))
                .kycExpiryDate(formatDate(response.details().documentExpiry()))
                .createdDate(Instant.now())
                .updatedDate(Instant.now())
                .build();
    }

    private Instant formatDate(String date) {
        final var DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, DATE_FORMATTER).atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
