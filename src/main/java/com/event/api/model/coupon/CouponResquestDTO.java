package com.event.api.model.coupon;

import java.util.Date;

public record CouponResquestDTO(String code, Integer discount, Date valid) {
}
