package com.solvd.qaprotours.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ermakovich Kseniya
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {

    private JwtAccess access;
    private Refresh refresh;

}
