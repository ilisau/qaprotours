package com.solvd.qaprotours.web.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "jwt tokens for user (access, refresh)")
public class JwtResponseDto {

    private JwtAccessDto accessDto;

    @Schema(description = "refresh token")
    private RefreshDto refreshDto;

}
