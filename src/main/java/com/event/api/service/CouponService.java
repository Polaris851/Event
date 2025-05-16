package com.event.api.service;

import com.event.api.model.coupon.Coupon;
import com.event.api.model.coupon.CouponRequestDTO;
import com.event.api.model.event.Event;
import com.event.api.repositories.CouponRepository;
import com.event.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;

    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO data) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(data.code());
        coupon.setDiscount(data.discount());
        coupon.setValid(data.valid());
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }
}
