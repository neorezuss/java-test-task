package Roles;

import java.io.Serializable;

public class Role implements Comparable<Role>, Serializable {
    private String roleName;
    private int roleLevel;
    public static final int MAX_ROLE_LEVEL = 3;

    public Role(String roleName, int roleLevel) {
        this.roleName = roleName;
        if (roleLevel < 1 || roleLevel > MAX_ROLE_LEVEL) {
            this.roleLevel = 1;
        } else {
            this.roleLevel = roleLevel;
        }
    }

    @Override
    public int compareTo(Role o) {
        if (roleLevel != o.getRoleLevel()) {
            return Integer.compare(roleLevel, o.getRoleLevel());
        }
        return roleName.compareTo(o.getRoleName());
    }

    public String getRoleName() {
        return roleName;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    @Override
    public String toString() {
        return roleName + "(" + roleLevel + " LVL)";
    }
}
