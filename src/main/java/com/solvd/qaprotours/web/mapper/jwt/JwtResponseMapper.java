package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.web.dto.jwt.JwtResponseDto;
import org.mapstruct.Mapper;

/**
 * @author Ermakovich Kseniya
 */
@Mapper(componentModel = "spring", uses = {JwtAccessMapper.class, RefreshMapper.class})
public interface JwtResponseMapper {

    JwtResponseDto toDto(JwtResponse response);

}
