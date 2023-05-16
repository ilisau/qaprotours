package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.web.dto.jwt.JwtTokenDto;
import org.mapstruct.Mapper;

/**
 * @author Ermakovich Kseniya
 */
@Mapper(componentModel = "spring")
public interface JwtTokenMapper {

    /**
     * Converts JwtTokenDto to JwtToken.
     * @param jwtTokenDto JwtTokenDto object.
     * @return JwtToken object.
     */
    JwtToken toEntity(JwtTokenDto jwtTokenDto);

    /**
     * Converts JwtToken to JwtTokenDto.
     * @param jwtToken JwtToken object.
     * @return JwtTokenDto object.
     */
    JwtTokenDto toDto(JwtToken jwtToken);

}
