package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.persistence.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.persistence.repository.CouponSelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponSelectionService {

    private final CouponSelectionRepository repository;

    public List<CouponSelectionEntity> getAllByCouponId(Long couponId){
        return repository.findAllByCouponId(couponId);
    }

    public List<CouponSelectionEntity> saveAll(List<CouponSelectionEntity> couponSelections){
        return repository.saveAll(couponSelections);
    }

}
