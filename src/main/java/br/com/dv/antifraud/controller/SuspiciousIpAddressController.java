package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpInfo;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;
import br.com.dv.antifraud.service.SuspiciousIpAddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
@Validated
public class SuspiciousIpAddressController {

    private final SuspiciousIpAddressService ipService;

    public SuspiciousIpAddressController(SuspiciousIpAddressService ipService) {
        this.ipService = ipService;
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<SuspiciousIpResponse> saveSuspiciousIp(@Valid @RequestBody SuspiciousIpInfo ipInfo) {
        SuspiciousIpResponse response = ipService.saveSuspiciousIp(ipInfo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<SuspiciousIpDeletionResponse> deleteSuspiciousIp(
            @PathVariable
            @Pattern(
                    regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$",
                    message = "Invalid IPv4 format."
            )
            String ip
    ) {
        SuspiciousIpDeletionResponse response = ipService.deleteSuspiciousIp(ip);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/suspicious-ip")
    public ResponseEntity<List<SuspiciousIpResponse>> getAllSuspiciousIps() {
        List<SuspiciousIpResponse> response = ipService.getAllSuspiciousIps();
        return ResponseEntity.ok(response);
    }

}
