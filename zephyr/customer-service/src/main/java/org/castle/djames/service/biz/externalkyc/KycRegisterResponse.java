package org.castle.djames.service.biz.externalkyc;

public record KycRegisterResponse(String status, String referenceId, KycRegisterResponseDetail details) {
}

