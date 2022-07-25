package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.persistence.entity.EventEntity;
import com.bilyoner.assignment.couponapi.persistence.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponSelectionService couponSelectionService;

    @Test
    public void shouldGetAllCouponsByStatus(){
        var couponStatus = CouponStatusEnum.PLAYED;
        couponService.getAllCouponsByCouponStatus(couponStatus);

        verify(couponRepository).findAllByStatus(couponStatus);
    }


    @Test
    public void shouldGetPlayedCoupons(){

        var userId = 1L;

        couponService.getPlayedCoupons(userId);

        verify(couponRepository).findAllByUserIdAndStatus(1L, CouponStatusEnum.PLAYED);
    }

}
