package ru.kata.spring.boot_security;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.model.Role;
import ru.kata.spring.boot_security.model.User;
import ru.kata.spring.boot_security.service.RoleService;
import ru.kata.spring.boot_security.service.UserService;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitClass {
    private final UserService userService;
    private final RoleService roleService;

    public InitClass(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createUsers() {
        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        roleService.addRole(user);
        roleService.addRole(admin);

        Set<Role> roleU = new HashSet<>();

        roleU.add(user);
        User user1 = new User();
        user1.setName("user");
        user1.setUsername("user");
        user1.setPassword("user");
        user1.setLastname("user");
        user1.setRoles(roleU);

        Set<Role> roleSu = new HashSet<>();

        roleSu.add(admin);
        User user2 = new User();
        user2.setName("admin");
        user2.setUsername("admin");
        user2.setPassword("admin");
        user2.setLastname("admin");
        user2.setRoles(roleU);
        user2.setRoles(roleSu);

        userService.save(user1);
        userService.save(user2);


    }
}
