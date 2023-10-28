package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.card.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.card.StolenCardRequest;
import br.com.dv.antifraud.dto.card.StolenCardResponse;

import java.util.List;

public interface StolenCardService {

    StolenCardResponse saveStolenCard(StolenCardRequest request);

    StolenCardDeletionResponse deleteStolenCard(String cardNumber);

    List<StolenCardResponse> getAllStolenCards();

}
