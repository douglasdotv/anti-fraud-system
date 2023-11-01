package br.com.dv.antifraud.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INITIAL_ALLOWED_MAX;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INITIAL_MANUAL_PROCESSING_MAX;

@Entity
@Getter
@Setter
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "allowed_min", nullable = false)
    private Long allowedMin = 0L;

    @Column(name = "allowed_max", nullable = false)
    private Long allowedMax = INITIAL_ALLOWED_MAX;

    @Column(name = "manual_processing_min", nullable = false)
    private Long manualProcessingMin = INITIAL_ALLOWED_MAX + 1;

    @Column(name = "manual_processing_max", nullable = false)
    private Long manualProcessingMax = INITIAL_MANUAL_PROCESSING_MAX;

    @Column(name = "prohibited_min", nullable = false)
    private Long prohibitedMin = INITIAL_MANUAL_PROCESSING_MAX + 1;

    @Column(name = "prohibited_max", nullable = false)
    private Long prohibitedMax = Long.MAX_VALUE;

}
