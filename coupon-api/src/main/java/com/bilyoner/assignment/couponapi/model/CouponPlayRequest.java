package com.bilyoner.assignment.couponapi.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponPlayRequest {

    private Long userId;
    private List<Long> couponIds;
}
