package br.com.dv.antifraud.repository;

import br.com.dv.antifraud.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
