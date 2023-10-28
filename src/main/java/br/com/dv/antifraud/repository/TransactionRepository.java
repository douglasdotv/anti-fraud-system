package br.com.dv.antifraud.repository;

import br.com.dv.antifraud.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
