package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.web.dto.TourDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TourMapper {

    /**
     * Converts Tour to TourDto.
     * @param tourDto TourDto object.
     * @return TourDto object.
     */
    Tour toEntity(TourDto tourDto);

    /**
     * Converts List of Tour to List of TourDto.
     * @param tours List of Tour objects.
     * @return List of TourDto objects.
     */
    List<TourDto> toDto(List<Tour> tours);

    /**
     * Converts Tour to TourDto.
     * @param tour Tour object.
     * @return TourDto object.
     */
    TourDto toDto(Tour tour);

}
