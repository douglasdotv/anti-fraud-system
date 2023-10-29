package br.com.dv.antifraud.service.transaction;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionRequest request);

}
