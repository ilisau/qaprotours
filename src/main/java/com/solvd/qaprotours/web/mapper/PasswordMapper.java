package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.user.Password;
import com.solvd.qaprotours.web.dto.user.PasswordDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordMapper {

    /**
     * Converts PasswordDto to Password.
     * @param dto PasswordDto object.
     * @return Password object.
     */
    Password toEntity(PasswordDto dto);

    /**
     * Converts Password to PasswordDto.
     * @param password Password object.
     * @return PasswordDto object.
     */
    PasswordDto toDto(Password password);

}
