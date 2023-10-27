package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.transaction.TransactionInfo;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.StolenCardRepository;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

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
        TransactionResult result = TransactionResult.fromAmount(amount);

        boolean isStolenCard = stolenCardRepository.findByCardNumber(transactionInfo.number()).isPresent();
        boolean isSuspiciousIp = suspiciousIpRepository.findByIpAddress(transactionInfo.ip()).isPresent();

        if (isStolenCard || isSuspiciousIp) {
            result = TransactionResult.PROHIBITED;
        }

        String info = getInfo(result, amount, isStolenCard, isSuspiciousIp);

        return new TransactionResponse(result.name(), info);
    }

    private String getInfo(TransactionResult result, Long amount, boolean stolenCard, boolean suspiciousIp) {
        switch (result) {
            case ALLOWED -> {
                return "none";
            }
            case MANUAL_PROCESSING -> {
                return "amount";
            }
            case PROHIBITED -> {
                List<String> reasons = new ArrayList<>();

                if (amount > 1500) reasons.add("amount");
                if (stolenCard) reasons.add("card-number");
                if (suspiciousIp) reasons.add("ip");

                return String.join(", ", reasons);
            }
            default -> {
                return "";
            }
        }
    }

}
