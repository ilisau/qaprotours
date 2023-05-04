package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.web.dto.TourCriteriaDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TourCriteriaMapper {

    /**
     * Converts TourCriteria to TourCriteriaDto.
     * @param tourCriteria TourCriteria object.
     * @return TourCriteriaDto object.
     */
    TourCriteriaDto toDto(TourCriteria tourCriteria);

    /**
     * Converts TourCriteriaDto to TourCriteria.
     * @param tourCriteriaDto TourCriteriaDto object.
     * @return TourCriteria object.
     */
    TourCriteria toEntity(TourCriteriaDto tourCriteriaDto);

}
