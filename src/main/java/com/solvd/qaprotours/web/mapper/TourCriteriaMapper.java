package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.TourCriteria;
import com.solvd.qaprotours.web.dto.TourCriteriaDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TourCriteriaMapper {

    TourCriteriaDto toDto(TourCriteria tourCriteria);

    TourCriteria toEntity(TourCriteriaDto tourCriteriaDto);

}
