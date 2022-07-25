package com.bilyoner.assignment.couponapi.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CouponSelectionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coupon_id")
    private CouponEntity coupon;


    private Long eventId;

}