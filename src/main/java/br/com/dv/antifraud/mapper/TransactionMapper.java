package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionOutcome;
import br.com.dv.antifraud.entity.Transaction;

import java.util.List;

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

    public static TransactionOutcome entityToOutcomeDto(Transaction transaction) {
        return new TransactionOutcome(transaction.getResult(), transaction.getInfo());
    }

    public static TransactionResponse entityToResponseDto(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getIp(),
                transaction.getCardNumber(),
                transaction.getRegion().name(),
                transaction.getDate(),
                transaction.getResult().name(),
                transaction.getFeedback()
        );
    }

    public static List<TransactionResponse> entityToResponseDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionMapper::entityToResponseDto)
                .toList();
    }

}
