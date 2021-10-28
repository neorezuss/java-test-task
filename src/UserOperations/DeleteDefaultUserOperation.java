package UserOperations;

import FileOperations.FileOperations;
import Users.User;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DeleteDefaultUserOperation implements DeleteUserOperation {
    private List<User> users;
    private FileOperations fileOperations;
    private Scanner consoleInputScanner;

    public DeleteDefaultUserOperation(List<User> users, FileOperations fileOperations) {
        this.users = users;
        this.fileOperations = fileOperations;
        this.consoleInputScanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        if (users.size() == 0) {
            System.out.println("Список пользователей пуст. Удаление не доступно.");
        } else {
            System.out.println("Список пользователей:");
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + 1 + ". " + users.get(i).shortUserInformation());
            }
            int selectedItem;
            while (true) {
                System.out.println("Выберите пользователя для удаления:");
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
            users.remove(selectedItem - 1);
            try {
                fileOperations.saveToFile(users);
                System.out.println("Пользователь успешно удален!");
            } catch (IOException e) {
                System.out.println("При удалении пользователя произошла ошибка!");
            }
        }
    }
}
