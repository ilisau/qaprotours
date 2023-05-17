package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.hotel.Hotel;
import com.solvd.qaprotours.service.HotelService;
import reactor.core.publisher.Mono;

public class FakeHotelService implements HotelService {

    @Override
    public Mono<Hotel> save(final Hotel hotel) {
        return Mono.empty();
    }

}
