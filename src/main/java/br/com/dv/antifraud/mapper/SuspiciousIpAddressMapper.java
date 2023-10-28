package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpRequest;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;
import br.com.dv.antifraud.entity.SuspiciousIpAddress;

import java.util.List;
import java.util.stream.Collectors;

public class SuspiciousIpAddressMapper {

    private static final String IP_REMOVAL_STATUS = "IP %s successfully removed!";

    public static SuspiciousIpAddress dtoToEntity(SuspiciousIpRequest ipInfo) {
        SuspiciousIpAddress ipAddress = new SuspiciousIpAddress();
        ipAddress.setIpAddress(ipInfo.ip());
        return ipAddress;
    }

    public static SuspiciousIpResponse entityToDto(SuspiciousIpAddress ipAddress) {
        return new SuspiciousIpResponse(ipAddress.getId(), ipAddress.getIpAddress());
    }

    public static List<SuspiciousIpResponse> entityToDtoList(List<SuspiciousIpAddress> suspiciousIpAddresses) {
        return suspiciousIpAddresses.stream()
                .map(SuspiciousIpAddressMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static SuspiciousIpDeletionResponse entityToDeletionDto(SuspiciousIpAddress ipAddress) {
        String deletionStatus = String.format(IP_REMOVAL_STATUS, ipAddress.getIpAddress());
        return new SuspiciousIpDeletionResponse(deletionStatus);
    }

}
