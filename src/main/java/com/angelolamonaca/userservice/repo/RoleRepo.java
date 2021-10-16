package com.angelolamonaca.userservice.repo;

import com.angelolamonaca.userservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 16/10/2021
 */
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
