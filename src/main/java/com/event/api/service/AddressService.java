package com.event.api.service;

import com.event.api.model.address.Address;
import com.event.api.model.event.Event;
import com.event.api.model.event.EventRequestDTO;
import com.event.api.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO data, Event event) {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.uf());
        address.setEvent(event);

        return addressRepository.save(address);
    }
}
