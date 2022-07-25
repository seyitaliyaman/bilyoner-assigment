package com.bilyoner.assignment.couponapi.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateRequest {

    private List<Long> eventIds;
    private BigDecimal couponCost;
}
