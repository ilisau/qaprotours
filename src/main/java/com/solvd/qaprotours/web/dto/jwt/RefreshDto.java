package com.solvd.qaprotours.web.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "refresh dto for jwt token")
public class RefreshDto {

    @Schema(description = "refresh token")
    private String token;

}
