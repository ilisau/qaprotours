package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.MailType;
import com.solvd.qaprotours.domain.user.User;

/**
 * @author Lisov Ilya
 */
public interface MailService {

    void sendMail(User receiver, MailType mailType);

}
