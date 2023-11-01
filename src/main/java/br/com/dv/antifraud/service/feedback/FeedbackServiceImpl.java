package br.com.dv.antifraud.service.feedback;

import br.com.dv.antifraud.dto.transaction.FeedbackRequest;
import br.com.dv.antifraud.dto.transaction.TransactionResponse;
import br.com.dv.antifraud.entity.Transaction;
import br.com.dv.antifraud.enums.TransactionResult;
import br.com.dv.antifraud.exception.custom.FeedbackAlreadyAssignedException;
import br.com.dv.antifraud.exception.custom.SameFeedbackAndResultException;
import br.com.dv.antifraud.mapper.TransactionMapper;
import br.com.dv.antifraud.repository.TransactionRepository;
import br.com.dv.antifraud.service.feedback.strategy.FeedbackStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.TRANSACTION_NOT_FOUND_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.FEEDBACK_ALREADY_ASSIGNED_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.SAME_RESULT_AND_FEEDBACK_MESSAGE;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final TransactionRepository transactionRepository;
    private final List<FeedbackStrategy> feedbackStrategies;

    public FeedbackServiceImpl(TransactionRepository transactionRepository,
                               List<FeedbackStrategy> feedbackStrategies) {
        this.transactionRepository = transactionRepository;
        this.feedbackStrategies = feedbackStrategies;
    }

    @Override
    @Transactional
    public TransactionResponse addFeedback(FeedbackRequest request) {
        Transaction transaction = transactionRepository.findById(request.transactionId())
                .orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE));

        TransactionResult transactionResult = transaction.getResult();
        String transactionFeedback = transaction.getFeedback();
        TransactionResult newFeedback = request.feedback();

        if (!transactionFeedback.isEmpty()) {
            throw new FeedbackAlreadyAssignedException(FEEDBACK_ALREADY_ASSIGNED_MESSAGE);
        }

        if (newFeedback == transactionResult) {
            throw new SameFeedbackAndResultException(SAME_RESULT_AND_FEEDBACK_MESSAGE);
        }

        for (FeedbackStrategy strategy : feedbackStrategies) {
            if (strategy.isApplicable(request, transaction)) {
                strategy.apply(request, transaction);
                break;
            }
        }

        transaction.setFeedback(newFeedback.name());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.entityToResponseDto(savedTransaction);
    }

}
