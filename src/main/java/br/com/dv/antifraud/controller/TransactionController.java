package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.TransactionInfo;
import br.com.dv.antifraud.dto.TransactionResponse;
import br.com.dv.antifraud.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> processTransaction(@Valid @RequestBody TransactionInfo transaction) {
        TransactionResponse transactionResponse = transactionService.processTransaction(transaction);
        return ResponseEntity.ok(transactionResponse);
    }

}
