package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.MailType;
import com.solvd.qaprotours.domain.user.User;

import java.util.Map;

/**
 * @author Lisov Ilya
 */
public interface MailService {

    void sendMail(User receiver, MailType mailType, Map<String, Object> params);

}
