package br.com.dv.antifraud.repository;

import br.com.dv.antifraud.entity.SuspiciousIpAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuspiciousIpAddressRepository extends JpaRepository<SuspiciousIpAddress, Long> {

    Optional<SuspiciousIpAddress> findByIpAddress(String ipAddress);

    List<SuspiciousIpAddress> findAllByOrderByIdAsc();

}
