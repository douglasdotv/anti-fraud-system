package br.com.dv.antifraud.service.card;

import br.com.dv.antifraud.dto.card.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.card.StolenCardRequest;
import br.com.dv.antifraud.dto.card.StolenCardResponse;
import br.com.dv.antifraud.entity.StolenCard;
import br.com.dv.antifraud.exception.custom.EntityAlreadyExistsException;
import br.com.dv.antifraud.mapper.StolenCardMapper;
import br.com.dv.antifraud.repository.StolenCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.STOLEN_CARD_ALREADY_EXISTS_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.STOLEN_CARD_NOT_FOUND_MESSAGE;

@Service
public class StolenCardServiceImpl implements StolenCardService {

    private final StolenCardRepository cardRepository;

    public StolenCardServiceImpl(StolenCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public StolenCardResponse saveStolenCard(StolenCardRequest request) {
        cardRepository.findByCardNumber(request.number()).ifPresent(card -> {
            throw new EntityAlreadyExistsException(STOLEN_CARD_ALREADY_EXISTS_MESSAGE);
        });

        StolenCard stolenCard = StolenCardMapper.dtoToEntity(request);
        StolenCard savedStolenCard = cardRepository.save(stolenCard);

        return StolenCardMapper.entityToDto(savedStolenCard);
    }

    @Override
    @Transactional
    public StolenCardDeletionResponse deleteStolenCard(String cardNumber) {
        StolenCard stolenCard = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException(STOLEN_CARD_NOT_FOUND_MESSAGE));

        cardRepository.delete(stolenCard);

        return StolenCardMapper.entityToDeletionDto(stolenCard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StolenCardResponse> getAllStolenCards() {
        List<StolenCard> stolenCards = cardRepository.findAllByOrderByIdAsc();
        return StolenCardMapper.entityToDtoList(stolenCards);
    }

}