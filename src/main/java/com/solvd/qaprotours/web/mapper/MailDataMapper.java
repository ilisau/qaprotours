package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.MailData;
import com.solvd.qaprotours.web.dto.MailDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MailDataMapper {

    MailDataDto toDto(MailData entity);

    MailData toEntity(MailDataDto userDto);

}
