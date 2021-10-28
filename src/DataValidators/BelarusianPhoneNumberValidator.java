package DataValidators;

public class BelarusianPhoneNumberValidator implements PhoneNumberValidator {
    @Override
    public boolean isValid(String phoneNumber) {
        return phoneNumber.trim().matches("(375)[0-9]{9}");
    }

    @Override
    public String warningMessage() {
        return "Номер телефона должен быть в формате 375XXXXXXXXX !";
    }
}
