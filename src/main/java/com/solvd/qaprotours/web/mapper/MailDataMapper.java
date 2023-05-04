package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.MailData;
import com.solvd.qaprotours.web.dto.MailDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MailDataMapper {

    /**
     * Converts MailData to MailDataDto.
     * @param entity MailData object.
     * @return MailDataDto object.
     */
    MailDataDto toDto(MailData entity);

    /**
     * Converts MailDataDto to MailData.
     * @param userDto MailDataDto object.
     * @return MailData object.
     */
    MailData toEntity(MailDataDto userDto);

}
