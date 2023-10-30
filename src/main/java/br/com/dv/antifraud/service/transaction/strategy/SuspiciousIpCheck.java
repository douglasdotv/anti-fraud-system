package br.com.dv.antifraud.service.transaction.strategy;

import br.com.dv.antifraud.constants.AntifraudSystemConstants;
import br.com.dv.antifraud.dto.transaction.TransactionRequest;
import br.com.dv.antifraud.enums.TransactionInfo;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.repository.SuspiciousIpAddressRepository;
import org.springframework.stereotype.Component;

@Component
public class SuspiciousIpCheck implements TransactionCheck {

    private final SuspiciousIpAddressRepository repository;

    public SuspiciousIpCheck(SuspiciousIpAddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean matchesCondition(TransactionRequest request) {
        return repository.findByIpAddress(request.ip()).isPresent();
    }

    @Override
    public TransactionResult getResult() {
        return TransactionResult.PROHIBITED;
    }

    @Override
    public String getInfo() {
        return TransactionInfo.IP.getValue();
    }

    @Override
    public int getSeverity() {
        return AntifraudSystemConstants.SEVERITY_PROHIBITED;
    }

}
