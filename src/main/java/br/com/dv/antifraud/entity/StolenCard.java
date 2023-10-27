package br.com.dv.antifraud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "stolen_card")
public class StolenCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stolen_card_id")
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

}
