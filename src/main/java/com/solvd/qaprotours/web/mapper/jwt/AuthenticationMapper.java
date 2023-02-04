package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.web.dto.jwt.AuthenticationDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    Authentication dtoToEntity(AuthenticationDto authenticationDto);

}
