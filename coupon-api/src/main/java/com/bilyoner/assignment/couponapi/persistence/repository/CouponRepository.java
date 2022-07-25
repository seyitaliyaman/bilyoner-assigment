package com.bilyoner.assignment.couponapi.persistence.repository;

import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    List<CouponEntity> findAllByStatus(CouponStatusEnum couponStatus);

    List<CouponEntity> findAllByIdIn(List<Long> couponIds);

    List<CouponEntity> findAllByUserIdAndStatus(Long userId, CouponStatusEnum status);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    CouponEntity findCouponEntityById(Long couponId);

    Optional<CouponEntity> findByIdAndStatus(Long couponId, CouponStatusEnum status);

}
