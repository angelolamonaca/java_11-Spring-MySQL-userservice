package com.angelolamonaca.userservice;

import com.angelolamonaca.userservice.domain.Role;
import com.angelolamonaca.userservice.domain.User;
import com.angelolamonaca.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

}

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "Itachi Uchiha", "itachi", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Naruto Uzumaki", "naruto", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Sasuke Uchiha", "sasuke", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Boruto Uzumaki", "boruto", "1234", new ArrayList<>()));

            userService.addRoleToUser("itachi", "ROLE_USER");
            userService.addRoleToUser("itachi", "ROLE_MANAGER");
            userService.addRoleToUser("naruto", "ROLE_MANAGER");
            userService.addRoleToUser("sasuke", "ROLE_ADMIN");
            userService.addRoleToUser("boruto", "ROLE_USER");
            userService.addRoleToUser("boruto", "ROLE_ADMIN");
            userService.addRoleToUser("boruto", "ROLE_SUPER_ADMIN");
        };
    }

}
