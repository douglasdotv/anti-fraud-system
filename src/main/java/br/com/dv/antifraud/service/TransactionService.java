package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.TransactionInfo;
import br.com.dv.antifraud.dto.TransactionResponse;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionInfo transactionInfo);

}
