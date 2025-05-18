package com.event.api.controller;

import com.event.api.model.event.Event;
import com.event.api.model.event.EventDetailsDTO;
import com.event.api.model.event.EventRequestDTO;
import com.event.api.model.event.EventResponseDTO;
import com.event.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody EventRequestDTO body) {
        Event newEvent = eventService.createEvent(body);
        return ResponseEntity.ok(newEvent);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable UUID eventId) {
        EventDetailsDTO eventDetails = eventService.getEventDetails(eventId);
        return ResponseEntity.ok(eventDetails);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<EventResponseDTO> allEvents = eventService.getUpComingEvents(page, size);
        return  ResponseEntity.ok(allEvents);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> getFilterEvents(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(required = false) String city,
                                                                  @RequestParam(required = false) String uf,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate
                                                                ) {
        List<EventResponseDTO> events = eventService.getFilterEvents(page, size, city, uf, startDate, endDate);
        return  ResponseEntity.ok(events);
    }
}
