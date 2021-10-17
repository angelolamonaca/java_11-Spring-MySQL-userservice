package com.angelolamonaca.userservice;

import com.angelolamonaca.userservice.domain.Role;
import com.angelolamonaca.userservice.domain.RoleType;
import com.angelolamonaca.userservice.domain.User;
import com.angelolamonaca.userservice.service.RoleService;
import com.angelolamonaca.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            roleService.saveRole(new Role(null, RoleType.ROLE_USER.name()));
            // userService.saveRole(new Role(null, RoleType.ROLE_MANAGER.name()));
            // userService.saveRole(new Role(null, RoleType.ROLE_ADMIN.name()));
            roleService.saveRole(new Role(null, RoleType.ROLE_SUPER_ADMIN.name()));

            userService.saveUser(new User(null, "Itachi Uchiha", "itachi@test.com", "Aa1@aaaaa", new ArrayList<>()));
            userService.saveUser(new User(null, "Boruto Uzumaki", "boruto@test.com", "Aa1@aaaaa", new ArrayList<>()));
            // userService.saveUser(new User(null, "Naruto Uzumaki", "naruto", "1234", new ArrayList<>()));
            // userService.saveUser(new User(null, "Sasuke Uchiha", "sasuke", "1234", new ArrayList<>()));

            roleService.addRoleToUser("boruto@test.com", RoleType.ROLE_USER.name());
            roleService.addRoleToUser("itachi@test.com", RoleType.ROLE_USER.name());
            roleService.addRoleToUser("itachi@test.com", RoleType.ROLE_SUPER_ADMIN.name());
        };
    }

}
