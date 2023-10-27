package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.card.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.card.StolenCardInfo;
import br.com.dv.antifraud.dto.card.StolenCardResponse;
import br.com.dv.antifraud.entity.StolenCard;
import br.com.dv.antifraud.exception.custom.EntityAlreadyExistsException;
import br.com.dv.antifraud.mapper.StolenCardMapper;
import br.com.dv.antifraud.repository.StolenCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StolenCardServiceImpl implements StolenCardService {

    private final StolenCardRepository cardRepository;

    public StolenCardServiceImpl(StolenCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public StolenCardResponse saveStolenCard(StolenCardInfo cardInfo) {
        cardRepository.findByCardNumber(cardInfo.number()).ifPresent(card -> {
            throw new EntityAlreadyExistsException("Stolen card already exists.");
        });

        StolenCard stolenCard = StolenCardMapper.dtoToEntity(cardInfo);
        StolenCard savedStolenCard = cardRepository.save(stolenCard);

        return StolenCardMapper.entityToDto(savedStolenCard);
    }

    @Override
    @Transactional
    public StolenCardDeletionResponse deleteStolenCard(String cardNumber) {
        StolenCard stolenCard = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Stolen card not found!"));

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