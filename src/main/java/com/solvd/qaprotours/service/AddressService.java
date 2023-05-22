package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.hotel.Address;
import reactor.core.publisher.Mono;

public interface AddressService {

    /**
     * Saves address.
     * @param address address
     * @return saved address
     */
    Mono<Address> save(Address address);

}
