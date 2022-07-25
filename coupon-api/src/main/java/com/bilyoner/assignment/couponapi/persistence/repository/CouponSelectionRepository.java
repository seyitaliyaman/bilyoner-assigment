package com.bilyoner.assignment.couponapi.persistence.repository;

import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponSelectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CouponSelectionRepository extends JpaRepository<CouponSelectionEntity, Long> {

    @Query(value = "SELECT * FROM COUPON_SELECTION_ENTITY where coupon_id = :couponId", nativeQuery = true)
    List<CouponSelectionEntity> findAllByCouponId(Long couponId);

    List<CouponSelectionEntity> findAllByCoupon_Status(CouponStatusEnum couponStatusEnum);
    List<CouponSelectionEntity> findAllByCoupon_UserIdAndCoupon_Status(Long userId, CouponStatusEnum couponStatusEnum);
    Set<CouponSelectionEntity> findAllByCoupon_IdIn(List<Long> couponIdList);
    Optional<CouponSelectionEntity> findByCoupon_Id(Long couponId);
}
