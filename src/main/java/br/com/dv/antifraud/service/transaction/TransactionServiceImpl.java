package br.com.dv.antifraud.service.transaction;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.entity.Transaction;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.mapper.TransactionMapper;
import br.com.dv.antifraud.repository.TransactionRepository;
import br.com.dv.antifraud.service.transaction.strategy.TransactionCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final List<TransactionCheck> transactionChecks;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  List<TransactionCheck> transactionChecks) {
        this.transactionRepository = transactionRepository;
        this.transactionChecks = transactionChecks;
    }

    @Override
    @Transactional
    public TransactionResponse processTransaction(TransactionRequest request) {
        Transaction transaction = TransactionMapper.dtoToEntity(request);

        TransactionResult result = getTransactionResult(request);
        transaction.setResult(result);

        String info = getTransactionInfo(request, result);
        transaction.setInfo(info);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.entityToDto(savedTransaction);
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

}
