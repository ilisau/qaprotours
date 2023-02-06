package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.user.UserTour;
import com.solvd.qaprotours.web.dto.user.UserTourDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface UserTourMapper {

    UserTour toEntity(UserTourDto userTourDto);

    UserTourDto toDto(UserTour userTour);

    List<UserTourDto> toDto(List<UserTour> userTours);

}
