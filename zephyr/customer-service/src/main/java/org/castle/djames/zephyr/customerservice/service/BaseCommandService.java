package org.castle.djames.zephyr.customerservice.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Consumer;
import org.castle.djames.zephyr.customerservice.dto.CustomerDetailResponse;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.entity.Customer;
import org.castle.djames.zephyr.customerservice.entity.KycStatus;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycRegisterRequest;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycRegisterResponse;

public abstract class BaseCommandService {

    protected KycRegisterRequest buildKycRegisterRequest(
        CustomerRequest customerRequest) {
        return KycRegisterRequest.builder()
            .firstName(customerRequest.firstName())
            .lastName(customerRequest.lastName())
            .nationalId(customerRequest.nationalId())
            .birthDate(customerRequest.birthDate())
            .email(customerRequest.email()).build();
    }

    protected CustomerDetailResponse buildCustomerDetailResponse(
        Customer customer) {
        return CustomerDetailResponse.builder().id(customer.getId())
            .firstName(customer.getFirstName()).lastName(customer.getLastName())
            .email(customer.getEmail()).phone(customer.getPhone())
            .kycStatus(customer.getKycStatus())
            .kycIssuedDate(customer.getKycIssuedDate())
            .kycExpiryDate(customer.getKycExpiryDate())
            .createdDate(Instant.now()).updatedDate(Instant.now()).build();
    }

    protected Customer buildCustomer(CustomerRequest customerRequest,
                                     KycRegisterResponse kycRegisterResponse) {
        return Customer.builder().firstName(customerRequest.firstName())
            .lastName(customerRequest.lastName())
            .nationalId(customerRequest.nationalId())
            .birthDate(customerRequest.birthDate())
            .address(customerRequest.address()).email(customerRequest.email())
            .phone(customerRequest.phone()).kycStatus(
                KycStatus.valueOf(kycRegisterResponse.status().toUpperCase()))
            .kycReferenceId(kycRegisterResponse.referenceId()).kycIssuedDate(
                formatDate(kycRegisterResponse.details().documentIssued()))
            .kycExpiryDate(
                formatDate(kycRegisterResponse.details().documentExpiry()))
            .build();
    }

    protected void updateCustomerFields(Customer customer, CustomerRequest request) {
        updateIfPresent(request.firstName(), customer::setFirstName);
        updateIfPresent(request.lastName(), customer::setLastName);
        updateIfPresent(request.email(), customer::setEmail);
        updateIfPresent(request.phone(), customer::setPhone);
        updateIfPresent(request.address(), customer::setAddress);
    }

    private void updateIfPresent(String value, Consumer<String> updater) {
        if (Objects.nonNull(value) && !value.isBlank()) {
            updater.accept(value);
        }
    }

    private Instant formatDate(String date) {
        final var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateFormatter).atStartOfDay()
            .toInstant(ZoneOffset.UTC);
    }
}
