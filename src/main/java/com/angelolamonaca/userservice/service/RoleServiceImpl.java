package com.angelolamonaca.userservice.service;

import com.angelolamonaca.userservice.domain.Role;
import com.angelolamonaca.userservice.domain.User;
import com.angelolamonaca.userservice.repo.RoleRepo;
import com.angelolamonaca.userservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 17/10/2021
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public List<Role> getRoles() {
        log.info("Fetching All roles");
        return roleRepo.findAll();
    }
}
