package com.solvd.qaprotours.service;

import com.solvd.qaprotours.web.dto.MailDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Lisov Ilya
 */
@FeignClient(value = "mail-client", path = "/api/v1/emails")
public interface MailClient {

    @PostMapping
    void sendMail(@RequestBody MailDataDto mailData);

}
