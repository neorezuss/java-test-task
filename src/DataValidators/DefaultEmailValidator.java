package DataValidators;

public class DefaultEmailValidator implements EmailValidator {
    @Override
    public boolean isValid(String email) {
        return email.trim().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+.[a-zA-z]{2,}");
    }

    @Override
    public String warningMessage() {
        return "Email должен быть в формате xxx@xxx.xxx !";
    }
}
