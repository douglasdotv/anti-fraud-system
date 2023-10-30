package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.ip.SuspiciousIpDeletionResponse;
import br.com.dv.antifraud.dto.ip.SuspiciousIpRequest;
import br.com.dv.antifraud.dto.ip.SuspiciousIpResponse;
import br.com.dv.antifraud.service.ip.SuspiciousIpAddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INVALID_IPV4_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.IPV4_REGEX;

@RestController
@RequestMapping("/api/antifraud")
@Validated
public class SuspiciousIpAddressController {

    private final SuspiciousIpAddressService ipService;

    public SuspiciousIpAddressController(SuspiciousIpAddressService ipService) {
        this.ipService = ipService;
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<SuspiciousIpResponse> saveSuspiciousIp(@Valid @RequestBody SuspiciousIpRequest request) {
        SuspiciousIpResponse response = ipService.saveSuspiciousIp(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<SuspiciousIpDeletionResponse> deleteSuspiciousIp(
            @PathVariable
            @Pattern(regexp = IPV4_REGEX, message = INVALID_IPV4_MESSAGE)
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
