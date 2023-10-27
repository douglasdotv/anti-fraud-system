package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.dto.card.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.card.StolenCardInfo;
import br.com.dv.antifraud.dto.card.StolenCardResponse;
import br.com.dv.antifraud.entity.StolenCard;

import java.util.List;
import java.util.stream.Collectors;

public class StolenCardMapper {

    private static final String CARD_REMOVAL_STATUS = "Card %s successfully removed!";

    public static StolenCard dtoToEntity(StolenCardInfo cardInfo) {
        StolenCard card = new StolenCard();
        card.setCardNumber(cardInfo.number());
        return card;
    }

    public static StolenCardResponse entityToDto(StolenCard card) {
        return new StolenCardResponse(card.getId(), card.getCardNumber());
    }

    public static List<StolenCardResponse> entityToDtoList(List<StolenCard> stolenCards) {
        return stolenCards.stream()
                .map(StolenCardMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static StolenCardDeletionResponse entityToDeletionDto(StolenCard card) {
        String deletionStatus = String.format(CARD_REMOVAL_STATUS, card.getCardNumber());
        return new StolenCardDeletionResponse(deletionStatus);
    }

}
