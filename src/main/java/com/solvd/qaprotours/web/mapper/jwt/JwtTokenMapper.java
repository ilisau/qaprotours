package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.web.dto.jwt.JwtTokenDto;
import org.mapstruct.Mapper;

/**
 * @author Ermakovich Kseniya
 */
@Mapper(componentModel = "spring")
public interface JwtTokenMapper {

    JwtToken toEntity(JwtTokenDto jwtTokenDto);

}
