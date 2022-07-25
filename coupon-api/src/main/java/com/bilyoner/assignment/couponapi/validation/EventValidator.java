package com.bilyoner.assignment.couponapi.validation;

import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.bilyoner.assignment.couponapi.persistence.entity.EventEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class EventValidator {


    public void validate(Set<EventEntity> events){
        validateMBS(events);
        validateType(events);
        validateDate(events);
    }

    private void validateMBS(Set<EventEntity> events){
        int maxMBS = events.stream().mapToInt(EventEntity::getMbs).min().orElseThrow(NoSuchElementException::new);
        if (events.size() < maxMBS)
            throw new CouponApiException(ErrorCodeEnum.MAX_MBS_ERROR);
    }

    private void validateType(Set<EventEntity> events){

        boolean isTennisExists = events.stream().anyMatch(event -> event.getType().equals(EventTypeEnum.TENNIS));
        boolean isFootballExists = events.stream().anyMatch(event -> event.getType().equals(EventTypeEnum.FOOTBALL));
        if (isFootballExists && isTennisExists)
            throw new CouponApiException(ErrorCodeEnum.BET_TYPE_ERROR);
    }

    private void validateDate(Set<EventEntity> events){
        boolean isOldEventExists = events.stream().anyMatch(event -> LocalDateTime.now().isAfter(event.getEventDate()));
        if (isOldEventExists)
            throw new CouponApiException(ErrorCodeEnum.STARTED_EVENT_ERROR);
    }

}
