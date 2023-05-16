package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.web.dto.TourDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TourMapper extends Mappable<Tour, TourDto> {
}
