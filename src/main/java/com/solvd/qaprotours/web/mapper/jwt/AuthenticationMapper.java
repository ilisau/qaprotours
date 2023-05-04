package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.web.dto.jwt.AuthenticationDto;
import org.mapstruct.Mapper;

/**
 * @author Ermakovich Kseniya
 */
@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    /**
     * Converts AuthenticationDto to Authentication.
     * @param authenticationDto AuthenticationDto object.
     * @return Authentication object.
     */
    Authentication toEntity(AuthenticationDto authenticationDto);

}
