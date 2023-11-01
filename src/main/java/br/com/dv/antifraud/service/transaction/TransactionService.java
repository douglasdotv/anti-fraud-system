package br.com.dv.antifraud.service.transaction;

import br.com.dv.antifraud.dto.transaction.TransactionHistoryResponse;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionRequest request);

    List<TransactionHistoryResponse> getTransactionHistory();

    List<TransactionHistoryResponse> getTransactionHistoryByCardNumber(String cardNumber);

}
