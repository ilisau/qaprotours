package com.solvd.qaprotours.web.mapper.jwt;

import com.solvd.qaprotours.domain.jwt.Refresh;
import com.solvd.qaprotours.web.dto.jwt.RefreshDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RefreshMapper {

    Refresh dtoToEntity(RefreshDto refreshDto);

}
