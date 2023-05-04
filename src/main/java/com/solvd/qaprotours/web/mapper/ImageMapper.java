package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.Image;
import com.solvd.qaprotours.web.dto.ImageDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface ImageMapper {

    /**
     * Converts Image to ImageDto.
     * @param imageDto ImageDto object.
     * @return ImageDto object.
     */
    Image toEntity(ImageDto imageDto);

}
