package FileOperations;

import Users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperationsImp implements FileOperations {
    private final static String FILE_PATH = "Users.txt";

    @Override
    public void saveToFile(List<User> userList) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(FILE_PATH)
        )) {
            objectOutputStream.writeObject(userList);
        }
    }

    @Override
    public List<User> loadFromFile() {
        List<User> userList;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(FILE_PATH))) {
            userList = ((ArrayList<User>) objectInputStream.readObject());
        } catch (ClassNotFoundException | IOException e) {
            userList = new ArrayList<>();
        }
        return userList;
    }
}
