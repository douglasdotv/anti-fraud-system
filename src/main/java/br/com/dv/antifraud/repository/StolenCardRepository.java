package br.com.dv.antifraud.repository;


import br.com.dv.antifraud.entity.StolenCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StolenCardRepository extends JpaRepository<StolenCard, Long> {

    Optional<StolenCard> findByCardNumber(String cardNumber);

    List<StolenCard> findAllByOrderByIdAsc();

}
