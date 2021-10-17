package com.angelolamonaca.userservice.api;

import com.angelolamonaca.userservice.domain.Role;
import com.angelolamonaca.userservice.service.RoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 17/10/2021
 */
@Slf4j
@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getUsers() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @PostMapping("/assign")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        roleService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String rolename;
}
