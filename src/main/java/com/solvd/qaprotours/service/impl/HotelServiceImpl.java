package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.hotel.Hotel;
import com.solvd.qaprotours.repository.HotelRepository;
import com.solvd.qaprotours.service.AddressService;
import com.solvd.qaprotours.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final AddressService addressService;

    @Override
    public Mono<Hotel> save(final Hotel hotel) {
        if (hotel.getAddress() != null) {
            addressService.save(hotel.getAddress()).subscribe();
        }
        return hotelRepository.save(hotel);
    }

}
