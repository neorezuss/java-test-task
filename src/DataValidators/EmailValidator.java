package DataValidators;

public interface EmailValidator {
    boolean isValid(String email);

    String warningMessage();
}
