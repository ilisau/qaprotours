package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.JwtRefresh;
import com.solvd.qaprotours.web.dto.jwt.JwtRefreshDto;
import org.mapstruct.Mapper;

/**
 * @author Ermakovich Kseniya
 */
@Mapper(componentModel = "spring")
public interface RefreshMapper {

    JwtRefresh toEntity(JwtRefreshDto jwtRefreshDto);

}
