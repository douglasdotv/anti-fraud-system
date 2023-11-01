package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.transaction.TransactionHistoryResponse;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.service.transaction.TransactionService;
import br.com.dv.antifraud.validation.CardNumber;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> processTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.processTransaction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistory() {
        List<TransactionHistoryResponse> response = transactionService.getTransactionHistory();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistoryByCardNumber(
            @PathVariable
            @CardNumber
            String number
    ) {
        List<TransactionHistoryResponse> response = transactionService.getTransactionHistoryByCardNumber(number);
        return ResponseEntity.ok(response);
    }

}
