package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.web.dto.user.PasswordDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordMapper extends Mappable<Password, PasswordDto> {
}
