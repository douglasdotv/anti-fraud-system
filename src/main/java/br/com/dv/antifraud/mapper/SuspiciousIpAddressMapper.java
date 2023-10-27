package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpInfo;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;
import br.com.dv.antifraud.entity.SuspiciousIpAddress;

import java.util.List;
import java.util.stream.Collectors;

public class SuspiciousIpAddressMapper {

    public static SuspiciousIpAddress dtoToEntity(SuspiciousIpInfo ipInfo) {
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
        String deletionStatus = String.format("IP %s successfully removed!", ipAddress.getIpAddress());
        return new SuspiciousIpDeletionResponse(deletionStatus);
    }

}
