package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.model.enums.TransactionTypeEnum;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.persistence.entity.EventEntity;
import com.bilyoner.assignment.couponapi.persistence.repository.CouponRepository;
import com.bilyoner.assignment.couponapi.persistence.repository.CouponSelectionRepository;
import com.bilyoner.assignment.couponapi.validation.CouponValidator;
import com.bilyoner.assignment.couponapi.validation.EventValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponSelectionService couponSelectionService;
    private final EventService eventService;
    private final BalanceService balanceService;
    private final CouponValidator couponValidator;

    @Cacheable(value = "coupons",unless="#result != null")
    public List<CouponDTO> getAllCouponsByCouponStatus(CouponStatusEnum couponStatus) {

        List<CouponDTO> response = new ArrayList<>();

        var coupons = couponRepository.findAllByStatus(couponStatus);

        coupons.forEach(coupon -> response.add(CouponDTO.builder()
                .id(coupon.getId())
                .userId(coupon.getUserId())
                .status(coupon.getStatus())
                .cost(coupon.getCost())
                .playDate(coupon.getPlayDate())
                .eventIds(couponSelectionService.getAllByCouponId(coupon.getId()).stream().map(CouponSelectionEntity::getEventId).collect(Collectors.toList()))
                .build()));

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "coupons",allEntries = true)
    public CouponDTO createCoupon(CouponCreateRequest couponCreateRequest) {

        eventService.checkEventIds(couponCreateRequest.getEventIds());

        var createdCoupon = couponRepository.save(CouponEntity.builder()
                .status(CouponStatusEnum.CREATED)
                .cost(couponCreateRequest.getCouponCost())
                .build());

        couponSelectionService.saveAll(
                couponCreateRequest.getEventIds()
                        .stream().
                        map(eventId -> CouponSelectionEntity.builder()
                                .eventId(eventId)
                                .coupon(createdCoupon)
                                .build()).collect(Collectors.toList()));

        return CouponDTO.mapToCouponDTO(
               createdCoupon,
               couponCreateRequest.getEventIds()
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "coupons",allEntries = true)
    public List<CouponDTO> playCoupons(CouponPlayRequest couponPlayRequest) {

        var totalCost = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
        List<CouponDTO> response = new ArrayList<>();
        couponPlayRequest.getCouponIds().forEach(couponId -> {

            final var coupon = couponRepository.findCouponEntityById(couponId);
            couponValidator.validateCouponIsPlayed(coupon);
            final var eventIds = couponSelectionService.getAllByCouponId(coupon.getId()).stream().map(CouponSelectionEntity::getEventId).collect(Collectors.toList());
            eventService.checkEventIds(eventIds);
            coupon.setUserId(couponPlayRequest.getUserId());
            coupon.setPlayDate(LocalDateTime.now());
            coupon.setStatus(CouponStatusEnum.PLAYED);
            totalCost.set(totalCost.get().add(coupon.getCost()));
            couponRepository.save(coupon);
            response.add(CouponDTO.mapToCouponDTO(coupon,eventIds));
        });

        balanceService.updateBalance(couponPlayRequest.getUserId(),totalCost.get(), TransactionTypeEnum.WITHDRAW);
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "coupons",allEntries = true)
    public CouponDTO cancelCoupon(Long couponId) {

        var cancelledCoupon = couponRepository.findByIdAndStatus(couponId, CouponStatusEnum.PLAYED).orElseThrow(() -> new CouponApiException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR));

        couponValidator.validateCouponIsCancelable(cancelledCoupon);

        balanceService.updateBalance(cancelledCoupon.getUserId(),cancelledCoupon.getCost(),TransactionTypeEnum.REFUND);

        cancelledCoupon.setStatus(CouponStatusEnum.CREATED);
        cancelledCoupon.setPlayDate(null);
        cancelledCoupon.setUserId(null);
        couponRepository.save(cancelledCoupon);
        return CouponDTO.mapToCouponDTO(cancelledCoupon,couponSelectionService.getAllByCouponId(couponId).stream().map(CouponSelectionEntity::getEventId).collect(Collectors.toList()));
    }


    public List<CouponDTO> getPlayedCoupons(Long userId) {


        return couponRepository.findAllByUserIdAndStatus(userId,CouponStatusEnum.PLAYED).stream()
                .map(coupon -> CouponDTO.mapToCouponDTO(
                        coupon,
                        couponSelectionService.getAllByCouponId(coupon.getId()).stream()
                                .map(CouponSelectionEntity::getEventId).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private BigDecimal calculateCouponsTotalCost(List<CouponEntity> coupons){
        return coupons.stream().map(CouponEntity::getCost).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
