package br.com.dv.antifraud.service.ip;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpRequest;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;
import br.com.dv.antifraud.entity.SuspiciousIpAddress;
import br.com.dv.antifraud.exception.custom.EntityAlreadyExistsException;
import br.com.dv.antifraud.mapper.SuspiciousIpAddressMapper;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.SUSPICIOUS_IP_ADDRESS_ALREADY_EXISTS_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.SUSPICIOUS_IP_ADDRESS_NOT_FOUND_MESSAGE;

@Service
public class SuspiciousIpAddressServiceImpl implements SuspiciousIpAddressService {

    private final SuspiciousIpAddressRepository ipRepository;

    public SuspiciousIpAddressServiceImpl(SuspiciousIpAddressRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    @Override
    @Transactional
    public SuspiciousIpResponse saveSuspiciousIp(SuspiciousIpRequest request) {
        ipRepository.findByIpAddress(request.ip()).ifPresent(ip -> {
            throw new EntityAlreadyExistsException(SUSPICIOUS_IP_ADDRESS_ALREADY_EXISTS_MESSAGE);
        });

        SuspiciousIpAddress suspiciousIp = SuspiciousIpAddressMapper.dtoToEntity(request);
        SuspiciousIpAddress savedSuspiciousIp = ipRepository.save(suspiciousIp);

        return SuspiciousIpAddressMapper.entityToDto(savedSuspiciousIp);
    }

    @Override
    @Transactional
    public SuspiciousIpDeletionResponse deleteSuspiciousIp(String ip) {
        SuspiciousIpAddress suspiciousIp = ipRepository.findByIpAddress(ip)
                .orElseThrow(() -> new EntityNotFoundException(SUSPICIOUS_IP_ADDRESS_NOT_FOUND_MESSAGE));

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