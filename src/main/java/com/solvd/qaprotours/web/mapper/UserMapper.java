package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.dto.user.UserDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts User to UserDto.
     * @param user User object.
     * @return UserDto object.
     */
    UserDto toDto(User user);

    /**
     * Converts UserDto to User.
     * @param userDto UserDto object.
     * @return User object.
     */
    User toEntity(UserDto userDto);

}
