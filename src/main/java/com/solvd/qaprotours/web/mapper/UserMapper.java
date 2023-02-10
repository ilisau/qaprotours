package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.dto.user.UserDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

}
