package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.card.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.card.StolenCardInfo;
import br.com.dv.antifraud.dto.card.StolenCardResponse;

import java.util.List;

public interface StolenCardService {

    StolenCardResponse saveStolenCard(StolenCardInfo cardInfo);

    StolenCardDeletionResponse deleteStolenCard(String cardNumber);

    List<StolenCardResponse> getAllStolenCards();

}
