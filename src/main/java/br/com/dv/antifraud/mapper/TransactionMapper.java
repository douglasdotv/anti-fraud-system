package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.entity.Transaction;

public class TransactionMapper {

    public static Transaction dtoToEntity(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.amount());
        transaction.setIp(transactionRequest.ip());
        transaction.setCardNumber(transactionRequest.number());
        transaction.setRegion(transactionRequest.region());
        transaction.setDate(transactionRequest.date());
        return transaction;
    }

    public static TransactionResponse entityToDto(Transaction transaction) {
        return new TransactionResponse(transaction.getResult(), transaction.getInfo());
    }

}
