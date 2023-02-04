package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.web.dto.jwt.JwtResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Ermakovich Kseniya
 */
@Mapper(componentModel = "spring", uses = {JwtAccessMapper.class, RefreshMapper.class})
public interface JwtResponseMapper {

    @Mapping(target = "accessDto", source = "access")
    @Mapping(target = "refreshDto", source = "refresh")
    JwtResponseDto entityToDto(JwtResponse response);

}
