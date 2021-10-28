package FileOperations;

import Users.User;

import java.io.IOException;
import java.util.List;

public interface FileOperations {
    void saveToFile(List<User> userList) throws IOException;

    List<User> loadFromFile() throws IOException, ClassNotFoundException;
}
