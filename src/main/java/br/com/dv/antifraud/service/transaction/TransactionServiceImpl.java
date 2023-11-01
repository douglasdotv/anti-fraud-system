package br.com.dv.antifraud.service.transaction;

import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionOutcome;
import br.com.dv.antifraud.entity.Card;
import br.com.dv.antifraud.entity.Transaction;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.exception.custom.TransactionsNotFoundException;
import br.com.dv.antifraud.mapper.TransactionMapper;
import br.com.dv.antifraud.repository.CardRepository;
import br.com.dv.antifraud.repository.TransactionRepository;
import br.com.dv.antifraud.service.transaction.strategy.TransactionCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.TRANSACTIONS_NOT_FOUND_MESSAGE;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final List<TransactionCheck> transactionChecks;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CardRepository cardRepository,
                                  List<TransactionCheck> transactionChecks) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.transactionChecks = transactionChecks;
    }

    @Override
    @Transactional
    public TransactionOutcome processTransaction(TransactionRequest request) {
        Transaction transaction = TransactionMapper.dtoToEntity(request);

        saveCardIfNotExists(request.number());

        TransactionResult result = getTransactionResult(request);
        String info = getTransactionInfo(request, result);

        transaction.setResult(result);
        transaction.setInfo(info);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.entityToOutcomeDto(savedTransaction);
    }

    private void saveCardIfNotExists(String cardNumber) {
        Optional<Card> cardOptional = cardRepository.findByCardNumber(cardNumber);

        if (cardOptional.isEmpty()) {
            Card card = new Card();
            card.setCardNumber(cardNumber);
            cardRepository.save(card);
        }
    }

    private TransactionResult getTransactionResult(TransactionRequest request) {
        int highestSeverity = 0;
        TransactionResult highestSeverityResult = TransactionResult.ALLOWED;

        for (TransactionCheck check : transactionChecks) {
            if (check.matchesCondition(request)) {
                int currentSeverity = check.getSeverity();
                if (currentSeverity > highestSeverity) {
                    highestSeverity = currentSeverity;
                    highestSeverityResult = check.getResult();
                }
            }
        }

        return highestSeverityResult;
    }

    private String getTransactionInfo(TransactionRequest request, TransactionResult highestSeverityResult) {
        List<String> reasons = new ArrayList<>();

        for (TransactionCheck check : transactionChecks) {
            if (check.matchesCondition(request) && check.getResult() == highestSeverityResult) {
                reasons.add(check.getInfo());
            }
        }

        Collections.sort(reasons);

        return String.join(", ", reasons);
    }

    public List<TransactionResponse> getTransactionHistory() {
        List<Transaction> transactions = transactionRepository.findAll();
        return TransactionMapper.entityToResponseDtoList(transactions);
    }

    public List<TransactionResponse> getTransactionHistoryByCardNumber(String cardNumber) {
        List<Transaction> transactions = transactionRepository.findAllByCardNumber(cardNumber);

        if (transactions.isEmpty()) {
            throw new TransactionsNotFoundException(TRANSACTIONS_NOT_FOUND_MESSAGE);
        }

        return TransactionMapper.entityToResponseDtoList(transactions);
    }

}
