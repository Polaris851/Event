package com.event.api.service;

import com.event.api.model.coupon.Coupon;
import com.event.api.model.event.Event;
import com.event.api.model.event.EventDetailsDTO;
import com.event.api.model.event.EventRequestDTO;
import com.event.api.model.event.EventResponseDTO;
import com.event.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private AddressService addressService;
    @Autowired
    private CouponService couponService;

    public Event createEvent(EventRequestDTO data) {
        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(data.date());
        newEvent.setRemote(data.remote());

        repository.save(newEvent);


        if(!data.remote()) {
            this.addressService.createAddress(data, newEvent);
        }

        return newEvent;
    }

    public List<EventResponseDTO> getUpComingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = repository.findUpComingEvents(new Date(), pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity():"",
                event.getAddress() != null ? event.getAddress().getUf():"",
                event.getRemote(),
                event.getEventUrl()
            )
        ).stream().toList();
    }

    public List<EventResponseDTO> getFilterEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate) {
        title = (title == null) ? "" : title;
        city = (city == null) ? "" : city;
        uf = (uf == null) ? "" : uf;
        startDate = startDate == null ? new Date() : startDate;
        endDate = endDate == null ? new Date(0) : endDate;

        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventsPage = repository.findFilteredEvent(title, city, uf, startDate, endDate, pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress() != null ? event.getAddress().getCity():"",
                        event.getAddress() != null ? event.getAddress().getUf():"",
                        event.getRemote(),
                        event.getEventUrl()
                )
        ).stream().toList();
    }

    public EventDetailsDTO getEventDetails(UUID eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());

        List<EventDetailsDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()
                )).collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity():"",
                event.getAddress() != null ? event.getAddress().getUf():"",
                event.getEventUrl(),
                couponDTOs
        );
    }
}
