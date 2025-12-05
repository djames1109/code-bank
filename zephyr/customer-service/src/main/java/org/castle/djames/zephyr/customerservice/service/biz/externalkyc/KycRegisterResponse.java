package org.castle.djames.zephyr.customerservice.service.biz.externalkyc;

public record KycRegisterResponse(String status, String referenceId,
                                  KycRegisterResponseDetail details) {
}

