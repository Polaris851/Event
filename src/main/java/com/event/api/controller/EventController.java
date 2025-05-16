package com.event.api.controller;

import com.event.api.model.event.Event;
import com.event.api.model.event.EventRequestDTO;
import com.event.api.model.event.EventResponseDTO;
import com.event.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody EventRequestDTO body) {
        Event newEvent = this.eventService.createEvent(body);
        return ResponseEntity.ok(newEvent);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<EventResponseDTO> allEvents = this.eventService.getUpComingEvents(page, size);
        return  ResponseEntity.ok(allEvents);
    }
}
