package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.transaction.FeedbackRequest;
import br.com.dv.antifraud.dto.transaction.TransactionOutcome;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.service.feedback.FeedbackService;
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
    private final FeedbackService feedbackService;

    public TransactionController(TransactionService transactionService, FeedbackService feedbackService) {
        this.transactionService = transactionService;
        this.feedbackService = feedbackService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionOutcome> processTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionOutcome response = transactionService.processTransaction(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/transaction")
    public ResponseEntity<TransactionResponse> addFeedback(@Valid @RequestBody FeedbackRequest request) {
        TransactionResponse response = feedbackService.addFeedback(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory() {
        List<TransactionResponse> response = transactionService.getTransactionHistory();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistoryByCardNumber(
            @CardNumber @PathVariable String number
    ) {
        List<TransactionResponse> response = transactionService.getTransactionHistoryByCardNumber(number);
        return ResponseEntity.ok(response);
    }

}
