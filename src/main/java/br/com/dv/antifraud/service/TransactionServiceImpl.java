package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.entity.Transaction;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.mapper.TransactionMapper;
import br.com.dv.antifraud.repository.StolenCardRepository;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import br.com.dv.antifraud.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final int ALLOWED_MAX_AMOUNT = 200;
    private static final int MANUAL_PROCESSING_MAX_AMOUNT = 1500;

    private final TransactionRepository transactionRepository;
    private final SuspiciousIpAddressRepository suspiciousIpRepository;
    private final StolenCardRepository stolenCardRepository;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            SuspiciousIpAddressRepository suspiciousIpRepository,
            StolenCardRepository stolenCardRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.stolenCardRepository = stolenCardRepository;
    }

    @Override
    @Transactional
    public TransactionResponse processTransaction(TransactionRequest request) {
        boolean isStolenCard = isStolenCard(request);
        boolean isSuspiciousIp = isSuspiciousIp(request);
        boolean isProhibitedBasedOnRegion = isProhibitedBasedOnRegion(request);
        boolean isProhibitedBasedOnIp = isProhibitedBasedOnIp(request);
        boolean isManualProcessingBasedOnRegion = isManualProcessingBasedOnRegion(request);
        boolean isManualProcessingBasedOnIp = isManualProcessingBasedOnIp(request);

        TransactionResult result = getTransactionResult(request, isStolenCard, isSuspiciousIp,
                isProhibitedBasedOnRegion, isProhibitedBasedOnIp,
                isManualProcessingBasedOnIp, isManualProcessingBasedOnRegion);

        String info = getTransactionInfo(result, request, isStolenCard, isSuspiciousIp,
                isProhibitedBasedOnRegion, isProhibitedBasedOnIp,
                isManualProcessingBasedOnIp, isManualProcessingBasedOnRegion);

        Transaction transaction = TransactionMapper.dtoToEntity(request);
        transaction.setResult(result);
        transaction.setInfo(info);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.entityToDto(savedTransaction);
    }

    private TransactionResult getTransactionResult(
            TransactionRequest request,
            boolean stolenCard,
            boolean suspiciousIp,
            boolean prohibitedBasedOnRegion,
            boolean prohibitedBasedOnIp,
            boolean manualProcessingBasedOnIp,
            boolean manualProcessingBasedOnRegion
    ) {
        if (stolenCard || suspiciousIp || prohibitedBasedOnRegion || prohibitedBasedOnIp ||
                request.amount() > MANUAL_PROCESSING_MAX_AMOUNT) {
            return TransactionResult.PROHIBITED;
        }

        if (manualProcessingBasedOnIp || manualProcessingBasedOnRegion || request.amount() > ALLOWED_MAX_AMOUNT) {
            return TransactionResult.MANUAL_PROCESSING;
        }

        return TransactionResult.ALLOWED;
    }

    private String getTransactionInfo(
            TransactionResult result,
            TransactionRequest request,
            boolean stolenCard,
            boolean suspiciousIp,
            boolean prohibitedBasedOnRegion,
            boolean prohibitedBasedOnIp,
            boolean manualProcessingBasedOnIp,
            boolean manualProcessingBasedOnRegion
    ) {
        switch (result) {
            case ALLOWED:
                return TransactionInfo.NONE.getValue();

            case MANUAL_PROCESSING:
                List<TransactionInfo> reasonsForManualProcessing = new ArrayList<>();

                if (request.amount() > ALLOWED_MAX_AMOUNT) reasonsForManualProcessing.add(TransactionInfo.AMOUNT);
                if (manualProcessingBasedOnIp) reasonsForManualProcessing.add(TransactionInfo.IP_CORRELATION);
                if (manualProcessingBasedOnRegion) reasonsForManualProcessing.add(TransactionInfo.REGION_CORRELATION);

                return reasonsForManualProcessing.stream()
                        .map(TransactionInfo::getValue)
                        .collect(Collectors.joining(", "));

            case PROHIBITED:
                List<TransactionInfo> reasonsForProhibited = new ArrayList<>();

                if (request.amount() > MANUAL_PROCESSING_MAX_AMOUNT) reasonsForProhibited.add(TransactionInfo.AMOUNT);
                if (stolenCard) reasonsForProhibited.add(TransactionInfo.CARD_NUMBER);
                if (suspiciousIp) reasonsForProhibited.add(TransactionInfo.IP);
                if (prohibitedBasedOnIp) reasonsForProhibited.add(TransactionInfo.IP_CORRELATION);
                if (prohibitedBasedOnRegion) reasonsForProhibited.add(TransactionInfo.REGION_CORRELATION);

                return reasonsForProhibited.stream()
                        .map(TransactionInfo::getValue)
                        .collect(Collectors.joining(", "));

            default:
                return "";
        }
    }

    private boolean isStolenCard(TransactionRequest request) {
        return stolenCardRepository.findByCardNumber(request.number()).isPresent();
    }

    private boolean isSuspiciousIp(TransactionRequest request) {
        return suspiciousIpRepository.findByIpAddress(request.ip()).isPresent();
    }

    private boolean isProhibitedBasedOnRegion(TransactionRequest request) {
        return getDistinctRegionsInLastHour(request) > 2;
    }

    private boolean isManualProcessingBasedOnRegion(TransactionRequest request) {
        return getDistinctRegionsInLastHour(request) == 2;
    }

    private Long getDistinctRegionsInLastHour(TransactionRequest request) {
        return transactionRepository.countDistinctRegionsInLastHour(
                request.number(), request.region(), request.date().minusHours(1), request.date()
        );
    }

    private boolean isProhibitedBasedOnIp(TransactionRequest request) {
        return getDistinctIpsInLastHour(request) > 2;
    }

    private boolean isManualProcessingBasedOnIp(TransactionRequest request) {
        return getDistinctIpsInLastHour(request) == 2;
    }

    private Long getDistinctIpsInLastHour(TransactionRequest request) {
        return transactionRepository.countDistinctIpsInLastHour(
                request.number(), request.ip(), request.date().minusHours(1), request.date()
        );
    }

}
