package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.field.Field;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.web.dto.TourCriteriaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TourCriteriaMapper
        extends Mappable<TourCriteria, TourCriteriaDto> {

    @Override
    @Mapping(target = "fields",
            expression = "java(map(dto))")
    TourCriteria toEntity(TourCriteriaDto dto);

    @Override
    @Mapping(target = "fields",
            expression = "java(map(dto))")
    List<TourCriteria> toEntity(List<TourCriteriaDto> dto);

    /**
     * Maps dto to list of fields.
     * @param dto TourCriteriaDto object
     * @return list of fields
     */
    default List<Field> map(TourCriteriaDto dto) {
        List<Field> fields = new ArrayList<>();
        fields.add(dto.getCountry());
        fields.add(dto.getTourType());
        fields.add(dto.getCateringType());
        fields.add(dto.getArrivalTime());
        fields.add(dto.getDepartureTime());
        fields.add(dto.getDayDuration());
        fields.add(dto.getPrice());
        return fields;
    }

}
