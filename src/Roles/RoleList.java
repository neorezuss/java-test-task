package Roles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoleList {
    private List<Role> roles;

    public RoleList() {
        roles = Arrays.asList(
                new Role("USER", 1),
                new Role("CUSTOMER", 1),
                new Role("ADMIN", 2),
                new Role("PROVIDER", 2),
                new Role("SUPER_ADMIN", 3)
        );
        Collections.sort(roles);
    }

    public List<Role> getRoleList() {
        return roles;
    }
}
