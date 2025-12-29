package org.castle.djames.zephyr.customerservice.flow.command.onboardcustomer;

import org.castle.djames.zephyr.customerservice.entity.KycStatus;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycRegisterResponse;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycRegisterResponseDetail;

public class OnboardCustomerExpectations {

    public static KycRegisterResponse buildKycRegisterResponse() {
        return new KycRegisterResponse(
            KycStatus.VERIFIED.name(),
            "KYC-REF-12345",
            new KycRegisterResponseDetail(
                "2020-01-01",
                "2030-01-01"
            )
        );
    }
}
