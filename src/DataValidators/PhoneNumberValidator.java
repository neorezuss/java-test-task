package DataValidators;

public interface PhoneNumberValidator {
    boolean isValid(String phoneNumber);

    String warningMessage();
}
