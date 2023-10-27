package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.StolenCardInfo;
import br.com.dv.antifraud.dto.StolenCardResponse;
import br.com.dv.antifraud.service.StolenCardService;
import br.com.dv.antifraud.validation.CardNumber;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
@Validated
public class StolenCardController {

    private final StolenCardService cardService;

    public StolenCardController(StolenCardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/stolencard")
    public ResponseEntity<StolenCardResponse> saveStolenCard(@Valid @RequestBody StolenCardInfo ipInfo) {
        StolenCardResponse response = cardService.saveStolenCard(ipInfo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<StolenCardDeletionResponse> deleteStolenCard(
            @PathVariable
            @CardNumber
            String number
    ) {
        StolenCardDeletionResponse response = cardService.deleteStolenCard(number);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stolencard")
    public ResponseEntity<List<StolenCardResponse>> getAllStolenCards() {
        List<StolenCardResponse> response = cardService.getAllStolenCards();
        return ResponseEntity.ok(response);
    }

}
