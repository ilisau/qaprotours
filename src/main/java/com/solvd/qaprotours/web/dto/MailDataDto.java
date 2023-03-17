package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.MailType;
import lombok.Data;

import java.util.Map;

@Data
public class MailDataDto {

    private MailType mailType;
    private Map<String, Object> params;

}
