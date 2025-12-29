package org.castle.djames.zephyr.customerservice.flow.command;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.castle.djames.zephyr.customerservice.repository.CustomerRepository;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

public abstract class BaseCommandTest {

    @InjectMock
    @RestClient
    protected KycService kycService;

    @InjectSpy
    protected CustomerRepository customerRepository;

}
