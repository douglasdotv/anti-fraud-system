package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class LastHourIpManualProcessingCheck implements TransactionCheck {

    private final TransactionRepository repository;

    public LastHourIpManualProcessingCheck(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        var count = repository.countDistinctIpsInLastHour(
                request.number(), request.ip(), request.date().minusHours(1), request.date());
        return count == 2;
    }

    @Override
    public TransactionResult getResult() {
        return TransactionResult.MANUAL_PROCESSING;
    }

    @Override
    public String getInfo() {
        return TransactionInfo.IP_CORRELATION.getValue();
    }

    @Override
    public int getSeverity() {
        return 1;
    }

}
