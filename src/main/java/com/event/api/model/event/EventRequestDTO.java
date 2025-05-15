package com.event.api.model.event;

import java.util.Date;

public record EventRequestDTO(String title, String description, Date date, String city, String uf, Boolean remote, String eventUrl) {

}
