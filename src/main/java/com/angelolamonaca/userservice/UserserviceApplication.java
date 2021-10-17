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
            roleService.saveRole(new Role(0L, RoleType.ROLE_USER.name()));
            // userService.saveRole(new Role(null, RoleType.ROLE_MANAGER.name()));
            // userService.saveRole(new Role(null, RoleType.ROLE_ADMIN.name()));
            roleService.saveRole(new Role(1L, RoleType.ROLE_SUPER_ADMIN.name()));

            userService.saveUser(new User(null, "Itachi Uchiha", "itachi", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Boruto Uzumaki", "boruto", "1234", new ArrayList<>()));
            // userService.saveUser(new User(null, "Naruto Uzumaki", "naruto", "1234", new ArrayList<>()));
            // userService.saveUser(new User(null, "Sasuke Uchiha", "sasuke", "1234", new ArrayList<>()));

            roleService.addRoleToUser("boruto", RoleType.ROLE_USER.name());
            roleService.addRoleToUser("itachi", RoleType.ROLE_USER.name());
            roleService.addRoleToUser("itachi", RoleType.ROLE_SUPER_ADMIN.name());
        };
    }

}
