package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.Tour;
import com.solvd.qaprotours.web.dto.TourDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TourMapper {

    Tour toEntity(TourDto tourDto);

    List<TourDto> toDto(List<Tour> tours);

    TourDto toDto(Tour tour);

}
