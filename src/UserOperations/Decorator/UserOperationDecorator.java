package UserOperations.Decorator;

import UserOperations.UserOperation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserOperationDecorator implements UserOperation {
    private UserOperation wrappee;
    private Scanner consoleInputScanner;

    public UserOperationDecorator(UserOperation wrappee) {
        this.wrappee = wrappee;
        this.consoleInputScanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        wrappee.execute();
        backMenu();
    }

    private void backMenu() {
        while (true) {
            System.out.println("Доступные действия:");
            System.out.println("1. Назад");
            try {
                int selectedItem = consoleInputScanner.nextInt();
                if (selectedItem == 1) {
                    break;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Попробуйте снова.");
                String inputCleaner = consoleInputScanner.nextLine();
            }
        }
    }
}
