package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.StolenCardInfo;
import br.com.dv.antifraud.dto.StolenCardResponse;

import java.util.List;

public interface StolenCardService {

    StolenCardResponse saveStolenCard(StolenCardInfo cardInfo);

    StolenCardDeletionResponse deleteStolenCard(String cardNumber);

    List<StolenCardResponse> getAllStolenCards();

}
