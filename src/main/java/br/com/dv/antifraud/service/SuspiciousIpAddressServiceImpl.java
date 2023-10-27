package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpInfo;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;
import br.com.dv.antifraud.entity.SuspiciousIpAddress;
import br.com.dv.antifraud.exception.custom.EntityAlreadyExistsException;
import br.com.dv.antifraud.mapper.SuspiciousIpAddressMapper;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SuspiciousIpAddressServiceImpl implements SuspiciousIpAddressService {

    private final SuspiciousIpAddressRepository ipRepository;

    public SuspiciousIpAddressServiceImpl(SuspiciousIpAddressRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    @Override
    @Transactional
    public SuspiciousIpResponse saveSuspiciousIp(SuspiciousIpInfo ipInfo) {
        ipRepository.findByIpAddress(ipInfo.ip()).ifPresent(suspiciousIp -> {
            throw new EntityAlreadyExistsException("Suspicious IP address already exists.");
        });

        SuspiciousIpAddress suspiciousIp = SuspiciousIpAddressMapper.dtoToEntity(ipInfo);
        SuspiciousIpAddress savedSuspiciousIp = ipRepository.save(suspiciousIp);

        return SuspiciousIpAddressMapper.entityToDto(savedSuspiciousIp);
    }

    @Override
    @Transactional
    public SuspiciousIpDeletionResponse deleteSuspiciousIp(String ip) {
        SuspiciousIpAddress suspiciousIp = ipRepository.findByIpAddress(ip)
                .orElseThrow(() -> new EntityNotFoundException("Suspicious IP address not found."));

        ipRepository.delete(suspiciousIp);

        return SuspiciousIpAddressMapper.entityToDeletionDto(suspiciousIp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousIpResponse> getAllSuspiciousIps() {
        List<SuspiciousIpAddress> suspiciousIpAddresses = ipRepository.findAllByOrderByIdAsc();
        return SuspiciousIpAddressMapper.entityToDtoList(suspiciousIpAddresses);
    }

}