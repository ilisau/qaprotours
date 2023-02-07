package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lisov Ilya
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {


}
