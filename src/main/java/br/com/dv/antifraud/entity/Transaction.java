package br.com.dv.antifraud.entity;

import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.enums.WorldRegion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private WorldRegion region;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private TransactionResult result;

    @Column(name = "info", nullable = false)
    private String info;

}
