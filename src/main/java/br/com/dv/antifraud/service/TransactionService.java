package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.transaction.TransactionInfo;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionInfo transactionInfo);

}
