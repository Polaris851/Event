package com.event.api.controller;

import com.event.api.model.coupon.Coupon;
import com.event.api.model.coupon.CouponRequestDTO;
import com.event.api.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("/{eventId}")
    public ResponseEntity<Coupon> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data) {
        Coupon coupon = couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupon);
    }
}
