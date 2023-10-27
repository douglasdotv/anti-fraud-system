package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpInfo;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;

import java.util.List;

public interface SuspiciousIpAddressService {

    SuspiciousIpResponse saveSuspiciousIp(SuspiciousIpInfo ipInfo);

    SuspiciousIpDeletionResponse deleteSuspiciousIp(String ip);

    List<SuspiciousIpResponse> getAllSuspiciousIps();

}
