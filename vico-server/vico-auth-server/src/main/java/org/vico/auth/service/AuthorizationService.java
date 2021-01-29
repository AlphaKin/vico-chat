package org.vico.auth.service;

import org.vico.auth.pojo.User;
import org.vico.auth.utils.Transfer;

public interface AuthorizationService {
    Transfer signIn(String type, User user);
    Transfer signUp(String type, User user);
    Transfer logout(String userId);
    String generateToken(User user);
    boolean verifyToken(String token);
}