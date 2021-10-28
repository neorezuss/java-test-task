package UserOperations;

import DataValidators.EmailValidator;
import DataValidators.PhoneNumberValidator;
import FileOperations.FileOperations;
import Roles.Role;
import Users.DefaultUser;
import Users.User;

import java.io.IOException;
import java.util.*;

public class UpdateDefaultUserOperation implements UpdateUserOperation {
    private List<User> users;
    private List<Role> roles;
    private FileOperations fileOperations;
    private PhoneNumberValidator phoneNumberValidator;
    private EmailValidator emailValidator;
    private Scanner consoleInputScanner;

    public UpdateDefaultUserOperation(List<User> users, List<Role> roles, FileOperations fileOperations,
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
        if (users.size() == 0) {
            System.out.println("Список пользователей пуст. Редактирование пользователей не доступно.");
        } else {
            System.out.println("Список пользователей:");
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + 1 + ". " + users.get(i).shortUserInformation());
            }
            int selectedItem;
            while (true) {
                System.out.println("Выберите пользователя для изменения:");
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
            DefaultUser user = (DefaultUser) users.get(selectedItem - 1);
            ChangeUserLabel:
            while (true) {
                System.out.println(user.UserInformation());
                this.showMenuItems();
                try {
                    selectedItem = consoleInputScanner.nextInt();
                    switch (selectedItem) {
                        case 1:
                            System.out.println("Введите новое имя пользователя:");
                            String firstName = consoleInputScanner.next();
                            user.setFirstName(firstName);
                            try {
                                fileOperations.saveToFile(users);
                                System.out.println("Имя пользователя успешно изменено!");
                            } catch (IOException e) {
                                System.out.println("При изменении имени пользователя произошла ошибка!");
                            }
                            break;
                        case 2:
                            System.out.println("Введите новую фамилию пользователя:");
                            String lastName = consoleInputScanner.next();
                            user.setLastName(lastName);
                            try {
                                fileOperations.saveToFile(users);
                                System.out.println("Фамилия пользователя успешно изменено!");
                            } catch (IOException e) {
                                System.out.println("При изменении фамилии пользователя произошла ошибка!");
                            }
                            break;
                        case 3:
                            System.out.println("Введите новый email:");
                            System.out.println(emailValidator.warningMessage());
                            String email = consoleInputScanner.next();
                            while (!emailValidator.isValid(email)) {
                                System.out.println("Email введен неверно, попробуйте снова:");
                                System.out.println(emailValidator.warningMessage());
                                email = consoleInputScanner.next();
                            }
                            user.setEmail(email);
                            try {
                                fileOperations.saveToFile(users);
                                System.out.println("Email пользователя успешно изменено!");
                            } catch (IOException e) {
                                System.out.println("При изменении email пользователя произошла ошибка!");
                            }
                            break;
                        case 4:
                            Map<Integer, Role> userRoles = user.getRoles();
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
                                            System.out.println("Другие выбранные роли были сброшены!");
                                        } else {
                                            if (userRoles.get(Role.MAX_ROLE_LEVEL) != null) {
                                                userRoles.remove(Role.MAX_ROLE_LEVEL);
                                                System.out.println("Роль максимального уровня была сброшена.");
                                            }
                                            userRoles.put(roles.get(selectedItem - 1).getRoleLevel(), roles.get(selectedItem - 1));
                                        }
                                        user.setRoles(userRoles);
                                        try {
                                            fileOperations.saveToFile(users);
                                            System.out.println("Роль  успешно применена!");
                                        } catch (IOException e) {
                                            System.out.println("При изменении ролей пользователя произошла ошибка!");
                                        }
                                    } else {
                                        throw new InputMismatchException();
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Неверный ввод! Попробуйте снова.");
                                    String inputCleaner = consoleInputScanner.nextLine();
                                }
                                while (true) {
                                    System.out.println("1. Продолжить изменение ролей");
                                    System.out.println("2. Завершить изменение ролей");
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
                            break;
                        case 5:
                            List<String> phoneNumbers = user.getPhoneNumbers();
                            String phoneNumber;
                            ChangePhoneNumbers:
                            while (true) {
                                while (true) {
                                    System.out.println("1. Добавить новый номер телефона.");
                                    System.out.println("2. Изменить существующий номер телефона");
                                    try {
                                        selectedItem = consoleInputScanner.nextInt();
                                        if (selectedItem == 1) {
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
                                            user.setPhoneNumbers(phoneNumbers);
                                            try {
                                                fileOperations.saveToFile(users);
                                                System.out.println("Телефон " + phoneNumber + " добавлен!");
                                            } catch (IOException e) {
                                                System.out.println("При добавлении телефона пользователя произошла ошибка!");
                                            }
                                            break;
                                        } else if (selectedItem == 2) {
                                            System.out.println("Список номеров телефона:");
                                            for (int i = 0; i < phoneNumbers.size(); i++) {
                                                System.out.println(i + 1 + ". " + phoneNumbers.get(i));
                                            }
                                            System.out.println("Выберите номер телефона для изменения:");
                                            selectedItem = consoleInputScanner.nextInt();
                                            System.out.println(phoneNumberValidator.warningMessage());
                                            phoneNumber = consoleInputScanner.next();
                                            while (!phoneNumberValidator.isValid(phoneNumber)) {
                                                System.out.println("Номер телефона введен неверно, попробуйте снова:");
                                                System.out.println(phoneNumberValidator.warningMessage());
                                                phoneNumber = consoleInputScanner.next();
                                            }
                                            phoneNumbers.set(selectedItem - 1, phoneNumber);
                                            user.setPhoneNumbers(phoneNumbers);
                                            try {
                                                fileOperations.saveToFile(users);
                                                System.out.println("Номер телефона успешно изменен!");
                                            } catch (IOException e) {
                                                System.out.println("При изменении номера телефона пользователя произошла ошибка!");
                                            }
                                            break;
                                        } else {
                                            throw new InputMismatchException();
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Неверный ввод! Попробуйте снова.");
                                        String inputCleaner = consoleInputScanner.nextLine();
                                    }
                                }
                                while (true) {
                                    System.out.println("1. Продолжить изменение номеров телефона");
                                    System.out.println("2. Завершить изменение номеров телефона");
                                    try {
                                        selectedItem = consoleInputScanner.nextInt();
                                        if (selectedItem == 1) {
                                            break;
                                        } else if (selectedItem == 2) {
                                            break ChangePhoneNumbers;
                                        } else {
                                            throw new InputMismatchException();
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Неверный ввод! Попробуйте снова.");
                                        String inputCleaner = consoleInputScanner.nextLine();
                                    }
                                }
                            }
                            break;
                        case 6:
                            break ChangeUserLabel;
                        default:
                            throw new InputMismatchException();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Неверный ввод! Попробуйте снова.");
                    String inputCleaner = consoleInputScanner.nextLine();
                }
            }
        }
    }

    private void showMenuItems() {
        System.out.println("Доступные действия:");
        System.out.println("1. Редактировать имя пользователя.");
        System.out.println("2. Редактировать фамилию пользователя.");
        System.out.println("3. Редактировать email пользователя.");
        System.out.println("4. Редактировать роли пользователя.");
        System.out.println("5. Редактировать номера телефонов пользователя.");
        System.out.println("6. Выход");
    }
}
