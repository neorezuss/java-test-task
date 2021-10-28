import DataValidators.BelarusianPhoneNumberValidator;
import DataValidators.DefaultEmailValidator;
import DataValidators.EmailValidator;
import DataValidators.PhoneNumberValidator;
import FileOperations.FileOperationsImp;
import FileOperations.FileOperations;
import Roles.RoleList;
import UserOperations.*;
import UserOperations.Decorator.UserOperationDecorator;
import Users.User;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {
    private List<User> users;
    private FileOperations fileOperations;
    private PhoneNumberValidator phoneNumberValidator;
    private EmailValidator emailValidator;
    private RoleList roles;
    private ShowUsersOperation showUsersOperation;
    private ShowUserInfoOperation showUserInfoOperation;
    private AddUserOperation addUserOperation;
    private UpdateUserOperation updateUserOperation;
    private DeleteUserOperation deleteUserOperation;
    private Scanner consoleInputScanner;

    public Application() throws IOException, ClassNotFoundException {
        fileOperations = new FileOperationsImp();
        users = fileOperations.loadFromFile();
        phoneNumberValidator = new BelarusianPhoneNumberValidator();
        emailValidator = new DefaultEmailValidator();
        roles = new RoleList();
        showUsersOperation = new ShowDefaultUsersOperation(users);
        showUserInfoOperation = new ShowDefaultUserInfoOperation(users);
        addUserOperation = new AddDefaultUserOperation(users, roles.getRoleList(), fileOperations, phoneNumberValidator, emailValidator);
        updateUserOperation = new UpdateDefaultUserOperation(users, roles.getRoleList(), fileOperations, phoneNumberValidator, emailValidator);
        deleteUserOperation = new DeleteDefaultUserOperation(users, fileOperations);
        consoleInputScanner = new Scanner(System.in);
    }

    public void start() {
        ExternalLabel:
        while (true) {
            this.showMenuItems();
            try {
                int selectedItem = consoleInputScanner.nextInt();
                switch (selectedItem) {
                    case 1:
                        UserOperationDecorator showUsersDecorator = new UserOperationDecorator(showUsersOperation);
                        showUsersDecorator.execute();
                        break;
                    case 2:
                        UserOperationDecorator showUserInfoDecorator = new UserOperationDecorator(showUserInfoOperation);
                        showUserInfoDecorator.execute();
                        break;
                    case 3:
                        UserOperationDecorator addUserDecorator = new UserOperationDecorator(addUserOperation);
                        addUserDecorator.execute();
                        break;
                    case 4:
                        updateUserOperation.execute();
                        break;
                    case 5:
                        UserOperationDecorator deleteUserDecorator = new UserOperationDecorator(deleteUserOperation);
                        deleteUserDecorator.execute();
                        break;
                    case 6:
                        System.out.println("Завершение работы. Всем пока.");
                        break ExternalLabel;
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Попробуйте снова.");
                String inputCleaner = consoleInputScanner.nextLine();
            }
        }
    }

    private void showMenuItems() {
        System.out.println("Доступные действия:");
        System.out.println("1. Список пользователей");
        System.out.println("2. Информация о пользователе");
        System.out.println("3. Добавить пользователя");
        System.out.println("4. Изменить пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("6. Выход");
    }
}
