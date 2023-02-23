package com.solvd.qaprotours.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lisov Ilya
 */
@Data
public class Image {

    private MultipartFile file;

}
