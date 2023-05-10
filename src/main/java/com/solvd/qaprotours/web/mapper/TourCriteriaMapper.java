package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.web.dto.TourCriteriaDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TourCriteriaMapper
        extends Mappable<TourCriteria, TourCriteriaDto> {
}
