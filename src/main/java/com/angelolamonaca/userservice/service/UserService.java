package com.angelolamonaca.userservice.service;

import com.angelolamonaca.userservice.domain.User;

import java.util.List;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 16/10/2021
 */
public interface UserService {
    User saveUser(User user);

    User getUser(String username);

    List<User> getUsers();
}
