package br.com.dv.antifraud.repository;

import br.com.dv.antifraud.entity.Transaction;
import br.com.dv.antifraud.enums.WorldRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT COUNT(DISTINCT t.region)
            FROM Transaction t
            WHERE t.cardNumber = :cardNumber
            AND t.region != :region
            AND t.date BETWEEN :oneHourBefore AND :now
            """)
    Long countDistinctRegionsInLastHour(String cardNumber, WorldRegion region,
                                        LocalDateTime oneHourBefore, LocalDateTime now);

    @Query("""
            SELECT COUNT(DISTINCT t.ip)
            FROM Transaction t
            WHERE t.cardNumber = :cardNumber
            AND t.ip != :ip
            AND t.date BETWEEN :oneHourBefore AND :now
            """)
    Long countDistinctIpsInLastHour(String cardNumber, String ip,
                                    LocalDateTime oneHourBefore, LocalDateTime now);

    List<Transaction> findAllByCardNumber(String cardNumber);

}
