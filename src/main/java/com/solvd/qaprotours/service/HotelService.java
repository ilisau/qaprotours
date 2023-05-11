package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.hotel.Hotel;
import reactor.core.publisher.Mono;

public interface HotelService {

    /**
     * Saves hotel.
     * @param hotel hotel
     * @return saved hotel
     */
    Mono<Hotel> save(Hotel hotel);

}
