package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.hotel.Hotel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HotelRepository extends ReactiveCrudRepository<Hotel, Long> {
}
