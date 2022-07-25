package com.bilyoner.assignment.balanceapi.service;

import com.bilyoner.assignment.balanceapi.exception.BalanceApiException;
import com.bilyoner.assignment.balanceapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import com.bilyoner.assignment.balanceapi.persistence.repository.UserBalanceRepository;
import com.bilyoner.assignment.balanceapi.validator.BalanceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceService {

    private final UserBalanceRepository repository;
    private final BalanceHistoryService balanceHistoryService;
    private final BalanceValidator balanceValidator;

    @Transactional(rollbackFor = Exception.class)
    public void updateBalance(UpdateBalanceRequest updateBalanceRequest) {


        log.info("Balance update service started # {}",updateBalanceRequest);
        var userBalanceEntity = getByUserIdWithLock(updateBalanceRequest.getUserId());
        if (TransactionTypeEnum.WITHDRAW.equals(updateBalanceRequest.getTransactionType()))
            balanceValidator.validateBalance(userBalanceEntity.getAmount(), updateBalanceRequest.getAmount());
        updateBalance(updateBalanceRequest,userBalanceEntity);
        log.info("Balance update service ended. # {}",updateBalanceRequest);
    }

    private UserBalanceEntity getByUserIdWithLock(Long userId){
        return repository.findByUserId(userId).orElseThrow(() -> new BalanceApiException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR));
    }

    private void updateBalance(UpdateBalanceRequest updateBalanceRequest, UserBalanceEntity userBalanceEntity){
        var oldBalance = userBalanceEntity.getAmount();
        switch (updateBalanceRequest.getTransactionType()){
            case REFUND:
            case DEPOSIT:
                userBalanceEntity.setAmount(userBalanceEntity.getAmount().add(updateBalanceRequest.getAmount()));
                break;
            case WITHDRAW:
                userBalanceEntity.setAmount(userBalanceEntity.getAmount().subtract(updateBalanceRequest.getAmount()));
                break;
            default:
                throw new BalanceApiException(ErrorCodeEnum.INSUFFICIENT_TRANSACTION_TYPE);
        }
        repository.save(userBalanceEntity);
        balanceHistoryService.createBalanceHistory(updateBalanceRequest,oldBalance, userBalanceEntity.getAmount());
    }

}
