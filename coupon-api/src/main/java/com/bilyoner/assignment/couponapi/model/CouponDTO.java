package com.bilyoner.assignment.couponapi.model;

import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.persistence.entity.CouponEntity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO implements Serializable {

    private Long id;
    private Long userId;
    private CouponStatusEnum status;
    private BigDecimal cost;
    private List<Long> eventIds;
    private LocalDateTime playDate;


    public static CouponDTO mapToCouponDTO(CouponEntity coupon, List<Long> eventIds) {
        return CouponDTO.builder()
                .id(coupon.getId())
                .userId(coupon.getUserId())
                .status(coupon.getStatus())
                .cost(coupon.getCost())
                .eventIds(eventIds)
                .playDate(coupon.getPlayDate())
                .build();
    }
}
