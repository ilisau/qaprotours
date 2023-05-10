package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.web.dto.jwt.JwtResponseDto;
import org.mapstruct.Mapper;

/**
 * @author Ermakovich Kseniya
 */
@Mapper(componentModel = "spring")
public interface JwtResponseMapper {

    /**
     * Converts JwtResponse to JwtResponseDto.
     * @param response JwtResponse object.
     * @return JwtResponseDto object.
     */
    JwtResponseDto toDto(JwtResponse response);

}
