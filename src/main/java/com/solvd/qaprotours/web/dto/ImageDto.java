package com.solvd.qaprotours.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lisov Ilya
 */
@Data
public class ImageDto {

    private MultipartFile file;

}
