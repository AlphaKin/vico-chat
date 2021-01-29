package org.vico.restful.service;

import org.springframework.stereotype.Service;
import org.vico.restful.utils.Transfer;

@Service
public interface UserService {
    Transfer getFriendList();
}
