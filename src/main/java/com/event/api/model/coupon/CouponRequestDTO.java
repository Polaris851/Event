package com.event.api.model.coupon;

import java.util.Date;

public record CouponRequestDTO(String code, Integer discount, Date valid) {
}
