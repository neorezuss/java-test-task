package UserOperations;

import Users.User;

import java.util.List;

public class ShowDefaultUsersOperation implements ShowUsersOperation {
    private List<User> users;

    public ShowDefaultUsersOperation(List<User> users) {
        this.users = users;
    }

    @Override
    public void execute() {
        if (users.size() == 0) {
            System.out.println("Список пользователей пуст.");
        } else {
            System.out.println("Список пользователей:");
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + 1 + ". " + users.get(i).shortUserInformation());
            }
        }
    }
}
