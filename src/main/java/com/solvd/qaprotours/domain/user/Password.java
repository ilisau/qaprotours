package com.solvd.qaprotours.domain.user;

import lombok.Data;

/**
 * @author Lisov Ilya
 */
@Data
public class Password {

    private String oldPassword;
    private String newPassword;

}
