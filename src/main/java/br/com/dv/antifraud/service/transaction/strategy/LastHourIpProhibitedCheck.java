package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.constants.AntifraudSystemConstants;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class LastHourIpProhibitedCheck implements TransactionCheck {

    private final TransactionRepository repository;

    public LastHourIpProhibitedCheck(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        var count = repository.countDistinctIpsInLastHour(
                request.number(), request.ip(), request.date().minusHours(1), request.date());

        return count > AntifraudSystemConstants.LAST_HOUR_IP_REGION_COUNT_THRESHOLD;
    }

    @Override
    public TransactionResult getResult() {
        return TransactionResult.PROHIBITED;
    }

    @Override
    public String getInfo() {
        return TransactionInfo.IP_CORRELATION.getValue();
    }

    @Override
    public int getSeverity() {
        return AntifraudSystemConstants.SEVERITY_PROHIBITED;
    }

}
