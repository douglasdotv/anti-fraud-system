package br.com.dv.antifraud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "suspicious_ip_address")
public class SuspiciousIpAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suspicious_ip_address_id")
    private Long id;

    @Column(name = "ip_address", nullable = false, unique = true)
    private String ipAddress;

}
