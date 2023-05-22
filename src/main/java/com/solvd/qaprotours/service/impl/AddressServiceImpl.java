package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.hotel.Address;
import com.solvd.qaprotours.repository.AddressRepository;
import com.solvd.qaprotours.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Mono<Address> save(final Address address) {
        return addressRepository.save(address);
    }

}
