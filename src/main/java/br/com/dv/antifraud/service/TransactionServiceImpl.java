package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.transaction.TransactionInfo;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.StolenCardRepository;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final String NONE = "none";
    private static final String AMOUNT = "amount";
    private static final String CARD_NUMBER = "card-number";
    private static final String IP = "ip";
    private final SuspiciousIpAddressRepository suspiciousIpRepository;
    private final StolenCardRepository stolenCardRepository;

    public TransactionServiceImpl(SuspiciousIpAddressRepository suspiciousIpRepository,
                                  StolenCardRepository stolenCardRepository) {
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.stolenCardRepository = stolenCardRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse processTransaction(TransactionInfo transactionInfo) {
        Long amount = transactionInfo.amount();
        boolean isStolenCard = stolenCardRepository.findByCardNumber(transactionInfo.number()).isPresent();
        boolean isSuspiciousIp = suspiciousIpRepository.findByIpAddress(transactionInfo.ip()).isPresent();

        TransactionResult result = getTransactionResult(amount, isStolenCard, isSuspiciousIp);
        String info = getTransactionInfo(result, amount, isStolenCard, isSuspiciousIp);

        return new TransactionResponse(result.name(), info);
    }

    private TransactionResult getTransactionResult(Long amount, boolean stolenCard, boolean suspiciousIp) {
        if (stolenCard || suspiciousIp) {
            return TransactionResult.PROHIBITED;
        } else if (amount <= 200) {
            return TransactionResult.ALLOWED;
        } else if (amount <= 1500) {
            return TransactionResult.MANUAL_PROCESSING;
        } else {
            return TransactionResult.PROHIBITED;
        }
    }

    private String getTransactionInfo(TransactionResult res, Long amount, boolean stolenCard, boolean suspiciousIp) {
        switch (res) {
            case ALLOWED:
                return NONE;
            case MANUAL_PROCESSING:
                return AMOUNT;
            case PROHIBITED:
                List<String> reasons = new ArrayList<>();

                if (amount > 1500) reasons.add(AMOUNT);
                if (stolenCard) reasons.add(CARD_NUMBER);
                if (suspiciousIp) reasons.add(IP);

                // Sorting just in case future updates mess with the order
                Collections.sort(reasons);

                return String.join(", ", reasons);
            default:
                return "";
        }
    }

}
