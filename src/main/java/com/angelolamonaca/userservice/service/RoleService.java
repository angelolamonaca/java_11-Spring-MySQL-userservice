package com.angelolamonaca.userservice.service;

import com.angelolamonaca.userservice.domain.Role;

import java.util.List;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 17/10/2021
 */
public interface RoleService {
    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    List<Role> getRoles();
}
