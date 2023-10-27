package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.SuspiciousIpInfo;
import br.com.dv.antifraud.dto.SuspiciousIpResponse;
import br.com.dv.antifraud.entity.SuspiciousIpAddress;
import br.com.dv.antifraud.exception.SuspiciousIpAddressAlreadyExistsException;
import br.com.dv.antifraud.mapper.SuspiciousIpAddressMapper;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SuspiciousIpAddressServiceImpl implements SuspiciousIpAddressService {

    private final SuspiciousIpAddressRepository ipRepository;

    public SuspiciousIpAddressServiceImpl(SuspiciousIpAddressRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    @Override
    @Transactional
    public SuspiciousIpResponse saveSuspiciousIp(SuspiciousIpInfo ipInfo) {
        Optional<SuspiciousIpAddress> suspiciousIpOptional = ipRepository.findByIpAddress(ipInfo.ip());

        if (suspiciousIpOptional.isPresent()) {
            throw new SuspiciousIpAddressAlreadyExistsException("Suspicious IP address already exists!");
        }

        SuspiciousIpAddress suspiciousIp = SuspiciousIpAddressMapper.dtoToEntity(ipInfo);
        SuspiciousIpAddress savedSuspiciousIp = ipRepository.save(suspiciousIp);

        return SuspiciousIpAddressMapper.entityToDto(savedSuspiciousIp);
    }

    @Override
    @Transactional
    public SuspiciousIpDeletionResponse deleteSuspiciousIp(String ip) {
        Optional<SuspiciousIpAddress> suspiciousIpOptional = ipRepository.findByIpAddress(ip);

        if (suspiciousIpOptional.isEmpty()) {
            throw new EntityNotFoundException("Suspicious IP address not found!");
        }

        SuspiciousIpAddress suspiciousIp = suspiciousIpOptional.get();
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