package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.StolenCardRepository;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public TransactionResponse processTransaction(TransactionRequest request) {
        Long amount = request.amount();
        boolean isStolenCard = stolenCardRepository.findByCardNumber(request.number()).isPresent();
        boolean isSuspiciousIp = suspiciousIpRepository.findByIpAddress(request.ip()).isPresent();

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
                return TransactionInfo.NONE.getValue();
            case MANUAL_PROCESSING:
                return TransactionInfo.AMOUNT.getValue();
            case PROHIBITED:
                List<TransactionInfo> reasons = new ArrayList<>();
                if (amount > 1500) reasons.add(TransactionInfo.AMOUNT);
                if (stolenCard) reasons.add(TransactionInfo.CARD_NUMBER);
                if (suspiciousIp) reasons.add(TransactionInfo.IP);
                return reasons.stream().map(TransactionInfo::getValue).collect(Collectors.joining(", "));
            default:
                return "";
        }
    }

}
