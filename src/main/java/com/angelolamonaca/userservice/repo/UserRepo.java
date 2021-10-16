package com.angelolamonaca.userservice.repo;

import com.angelolamonaca.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 16/10/2021
 */
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
