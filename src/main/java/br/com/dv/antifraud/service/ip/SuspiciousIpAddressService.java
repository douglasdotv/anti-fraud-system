package br.com.dv.antifraud.service.ip;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpRequest;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;

import java.util.List;

public interface SuspiciousIpAddressService {

    SuspiciousIpResponse saveSuspiciousIp(SuspiciousIpRequest ipInfo);

    SuspiciousIpDeletionResponse deleteSuspiciousIp(String ip);

    List<SuspiciousIpResponse> getAllSuspiciousIps();

}
