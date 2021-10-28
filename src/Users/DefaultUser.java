package Users;

import Roles.Role;

import java.util.List;
import java.util.Map;

public class DefaultUser implements User {
    private String firstName;
    private String lastName;
    private String email;
    private Map<Integer, Role> roles;
    private List<String> phoneNumbers;

    public DefaultUser(String firstName, String lastName, String email, Map<Integer, Role> roles, List<String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String shortUserInformation() {
        return firstName + " " + lastName;
    }

    @Override
    public String UserInformation() {
        return "Имя - " + firstName + "\n" +
                "Фамилия - " + lastName + "\n" +
                "Email - " + email + "\n" +
                "Роли - " + roles.values() + "\n" +
                "Мобильные телефоны - " + phoneNumbers;
    }

    public Map<Integer, Role> getRoles() {
        return roles;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Map<Integer, Role> roles) {
        this.roles = roles;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
