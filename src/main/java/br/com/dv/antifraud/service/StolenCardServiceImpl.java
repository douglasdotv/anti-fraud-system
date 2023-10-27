package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.StolenCardDeletionResponse;
import br.com.dv.antifraud.dto.StolenCardInfo;
import br.com.dv.antifraud.dto.StolenCardResponse;
import br.com.dv.antifraud.entity.StolenCard;
import br.com.dv.antifraud.exception.StolenCardAlreadyExistsException;
import br.com.dv.antifraud.mapper.StolenCardMapper;
import br.com.dv.antifraud.repository.StolenCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StolenCardServiceImpl implements StolenCardService {

    private final StolenCardRepository cardRepository;

    public StolenCardServiceImpl(StolenCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public StolenCardResponse saveStolenCard(StolenCardInfo cardInfo) {
        Optional<StolenCard> stolenCardOptional = cardRepository.findByCardNumber(cardInfo.number());

        if (stolenCardOptional.isPresent()) {
            throw new StolenCardAlreadyExistsException("Stolen card already exists!");
        }

        StolenCard stolenCard = StolenCardMapper.dtoToEntity(cardInfo);
        StolenCard savedStolenCard = cardRepository.save(stolenCard);

        return StolenCardMapper.entityToDto(savedStolenCard);
    }

    @Override
    @Transactional
    public StolenCardDeletionResponse deleteStolenCard(String cardNumber) {
        Optional<StolenCard> stolenCardOptional = cardRepository.findByCardNumber(cardNumber);

        if (stolenCardOptional.isEmpty()) {
            throw new EntityNotFoundException("Stolen card not found!");
        }

        StolenCard stolenCard = stolenCardOptional.get();
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