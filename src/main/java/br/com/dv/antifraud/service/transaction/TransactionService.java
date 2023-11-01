package br.com.dv.antifraud.service.transaction;

import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionOutcome;

import java.util.List;

public interface TransactionService {

    TransactionOutcome processTransaction(TransactionRequest request);

    List<TransactionResponse> getTransactionHistory();

    List<TransactionResponse> getTransactionHistoryByCardNumber(String cardNumber);

}
