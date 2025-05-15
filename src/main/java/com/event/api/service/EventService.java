package com.event.api.service;

import com.event.api.model.event.Event;
import com.event.api.model.event.EventRequestDTO;
import com.event.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public Event createEvent(EventRequestDTO data) {
        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(data.date());
        newEvent.setRemote(data.remote());

        repository.save(newEvent);

        return newEvent;
    }
}
