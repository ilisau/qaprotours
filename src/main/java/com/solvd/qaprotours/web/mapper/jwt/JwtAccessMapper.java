package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.JwtAccess;
import com.solvd.qaprotours.web.dto.jwt.JwtAccessDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtAccessMapper {

    JwtAccessDto entityToDto(JwtAccess access);

}
