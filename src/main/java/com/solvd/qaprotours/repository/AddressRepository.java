package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.hotel.Address;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AddressRepository
        extends ReactiveCrudRepository<Address, Long> {
}
