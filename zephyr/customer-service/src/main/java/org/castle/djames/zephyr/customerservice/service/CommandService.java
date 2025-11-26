package org.castle.djames.zephyr.customerservice.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.castle.djames.zephyr.customerservice.dto.CustomerDetailResponse;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.entity.Customer;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CommandService extends BaseCommandService {

    private final KycService kycService;

    public CommandService(@RestClient KycService kycService) {
        this.kycService = kycService;
    }


    /**
     * Onboards a new customer into the system. This includes performing KYC (Know Your Customer)
     * registration via an external service and saving the customer details to the database.
     * If a customer with the provided national ID already exists, an exception is thrown.
     *
     * @param customerRequest the details of the customer to be onboarded,
     *                        including first name, last name, national ID, birth date, email, phone, and address.
     * @return a {@code CustomerDetailResponse} object containing the details of the newly onboarded customer,
     * including KYC status and associated metadata.
     * @throws RuntimeException if a customer with the same national ID already exists in the system.
     */
    @Transactional
    public CustomerDetailResponse onboardCustomer(CustomerRequest customerRequest) {
        if (Customer.findByNationalId(customerRequest.nationalId()).isPresent()) {
            throw new RuntimeException("National ID is already registered");
        }

        var registerRequest = buildKycRegisterRequest(customerRequest);
        var kycRegisterResponse = kycService.register(registerRequest);

        var customer = buildCustomer(customerRequest, kycRegisterResponse);
        customer.persist();

        return buildCustomerDetailResponse(customer);
    }

}
