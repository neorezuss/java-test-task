package UserOperations;

import DataValidators.EmailValidator;
import DataValidators.PhoneNumberValidator;
import FileOperations.FileOperations;
import Roles.Role;
import Users.DefaultUser;
import Users.User;

import java.io.IOException;
import java.util.*;

public class AddDefaultUserOperation implements AddUserOperation {
    private List<User> users;
    private List<Role> roles;
    private FileOperations fileOperations;
    private PhoneNumberValidator phoneNumberValidator;
    private EmailValidator emailValidator;
    private Scanner consoleInputScanner;

    public AddDefaultUserOperation(List<User> users, List<Role> roles, FileOperations fileOperations,
                                   PhoneNumberValidator phoneNumberValidator, EmailValidator emailValidator) {
        this.users = users;
        this.roles = roles;
        this.fileOperations = fileOperations;
        this.phoneNumberValidator = phoneNumberValidator;
        this.emailValidator = emailValidator;
        this.consoleInputScanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("Добавление нового пользователя.");
        System.out.println("Введите имя пользователя:");
        String firstName = consoleInputScanner.nextLine();
        System.out.println("Введите фамилию пользователя:");
        String lastName = consoleInputScanner.nextLine();
        System.out.println("Введите email:");
        System.out.println(emailValidator.warningMessage());
        String email = consoleInputScanner.next();
        while (!emailValidator.isValid(email)) {
            System.out.println("Email введен неверно, попробуйте снова:");
            System.out.println(emailValidator.warningMessage());
            email = consoleInputScanner.next();
        }
        int selectedItem;
        Map<Integer, Role> userRoles = new HashMap<>();
        AddRoles:
        while (true) {
            System.out.println("Список ролей:");
            for (int i = 0; i < roles.size(); i++) {
                System.out.println(i + 1 + ". " + roles.get(i));
            }
            System.out.println("Выберите роль для добавления:");
            try {
                selectedItem = consoleInputScanner.nextInt();
                if (selectedItem >= 0 && selectedItem <= roles.size()) {
                    if (roles.get(selectedItem - 1).getRoleLevel() == Role.MAX_ROLE_LEVEL) {
                        userRoles.clear();
                        userRoles.put(roles.get(selectedItem - 1).getRoleLevel(), roles.get(selectedItem - 1));
                        System.out.println("Выбрана роль максимального уровня - " + roles.get(selectedItem - 1).getRoleName());
                        System.out.println("Выбор других ролей запрещен!");
                        break AddRoles;
                    } else if (userRoles.get(roles.get(selectedItem - 1).getRoleLevel()) == null) {
                        userRoles.put(roles.get(selectedItem - 1).getRoleLevel(), roles.get(selectedItem - 1));
                        System.out.println("Роль " + roles.get(selectedItem - 1).getRoleName() + " добавлена!");
                    } else {
                        System.out.println("У пользователя уже есть роль этого уровня!");
                    }
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Попробуйте снова.");
                String inputCleaner = consoleInputScanner.nextLine();
            }
            while (true) {
                System.out.println("1. Продолжить добавление ролей");
                System.out.println("2. Завершить добавление ролей");
                try {
                    selectedItem = consoleInputScanner.nextInt();
                    if (selectedItem == 1) {
                        break;
                    } else if (selectedItem == 2) {
                        break AddRoles;
                    } else {
                        throw new InputMismatchException();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Неверный ввод! Попробуйте снова.");
                    String inputCleaner = consoleInputScanner.nextLine();
                }
            }
        }
        List<String> phoneNumbers = new ArrayList<>();
        String phoneNumber;
        AddPhoneNumbers:
        while (true) {
            if (phoneNumbers.size() >= 3) {
                System.out.println("Добавлено максимальное количество номеров!");
                break;
            }
            System.out.println("Введите номер телефона для добавления:");
            System.out.println(phoneNumberValidator.warningMessage());
            phoneNumber = consoleInputScanner.next();
            while (!phoneNumberValidator.isValid(phoneNumber)) {
                System.out.println("Номер телефона введен неверно, попробуйте снова:");
                System.out.println(phoneNumberValidator.warningMessage());
                phoneNumber = consoleInputScanner.next();
            }
            phoneNumbers.add(phoneNumber);
            System.out.println("Телефон " + phoneNumber + " добавлен!");
            while (true) {
                System.out.println("1. Продолжить добавление номеров телефона");
                System.out.println("2. Завершить добавление номеров телефона");
                try {
                    selectedItem = consoleInputScanner.nextInt();
                    if (selectedItem == 1) {
                        break;
                    } else if (selectedItem == 2) {
                        break AddPhoneNumbers;
                    } else {
                        throw new InputMismatchException();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Неверный ввод! Попробуйте снова.");
                    String inputCleaner = consoleInputScanner.nextLine();
                }
            }
        }
        User newUser = new DefaultUser(firstName, lastName, email, userRoles, phoneNumbers);
        users.add(newUser);
        try {
            fileOperations.saveToFile(users);
            System.out.println("Новый пользователь успешно добавлен!");
        } catch (IOException e) {
            System.out.println("При добавлении пользователя произошла ошибка!");
        }
    }
}
