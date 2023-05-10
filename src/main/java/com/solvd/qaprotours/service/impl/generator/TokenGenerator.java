package com.solvd.qaprotours.service.impl.generator;

import com.solvd.qaprotours.domain.user.User;

public interface TokenGenerator {

    /**
     * Generates a token.
     * @param user user
     * @return token
     */
    String generate(User user);

}
