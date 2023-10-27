package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.SuspiciousIpInfo;
import br.com.dv.antifraud.dto.SuspiciousIpResponse;

import java.util.List;

public interface SuspiciousIpAddressService {

    SuspiciousIpResponse saveSuspiciousIp(SuspiciousIpInfo ipInfo);

    SuspiciousIpDeletionResponse deleteSuspiciousIp(String ip);

    List<SuspiciousIpResponse> getAllSuspiciousIps();

}
