package UserOperations;

import Users.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ShowDefaultUserInfoOperation implements ShowUserInfoOperation {
    private List<User> users;
    private Scanner consoleInputScanner;

    public ShowDefaultUserInfoOperation(List<User> users) {
        this.users = users;
        this.consoleInputScanner = new Scanner(System.in);
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
            int selectedItem;
            while (true) {
                System.out.println("Выберите номер пользователя:");
                try {
                    selectedItem = consoleInputScanner.nextInt();
                    if (selectedItem >= 0 && selectedItem <= users.size()) {
                        break;
                    } else {
                        throw new InputMismatchException();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Неверный ввод! Попробуйте снова.");
                    String inputCleaner = consoleInputScanner.nextLine();
                }
            }
            System.out.println(users.get(selectedItem - 1).UserInformation());
        }
    }
}
