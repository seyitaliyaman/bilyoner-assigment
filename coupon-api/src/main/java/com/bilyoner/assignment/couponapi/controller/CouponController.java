package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.CouponPlayRequest;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO : Implement missing parts
 */

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public List<CouponDTO> getAllCouponsByCouponStatus(@RequestParam CouponStatusEnum couponStatus)  {
        return couponService.getAllCouponsByCouponStatus(couponStatus);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CouponDTO createCoupon(@Valid @RequestBody CouponCreateRequest couponCreateRequest) {
        return couponService.createCoupon(couponCreateRequest);
    }

    @GetMapping("/played/{userId}")
    public List<CouponDTO> getPlayedCoupons(@PathVariable Long userId) {
        return couponService.getPlayedCoupons(userId);
    }

    @PutMapping
    public List<CouponDTO> playCoupons(@Valid @RequestBody CouponPlayRequest couponPlayRequest) {
        return couponService.playCoupons(couponPlayRequest);
    }

    @DeleteMapping("/{couponId}")
    public CouponDTO cancelCoupon(@PathVariable Long couponId) {
        return couponService.cancelCoupon(couponId);
    }
}