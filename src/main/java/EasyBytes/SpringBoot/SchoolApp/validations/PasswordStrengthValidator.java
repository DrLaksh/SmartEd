package EasyBytes.SpringBoot.SchoolApp.validations;

import EasyBytes.SpringBoot.SchoolApp.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator,String> {

    List<String> weakPassword; // list of weak password
//this are the methods implemented by ConstraintValidator
    @Override
    public void initialize(PasswordValidator passwordValidator) {
        weakPassword = Arrays.asList("12345" , "password" , "qwerty");//passed List to ignore
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext constraintValidatorContext) {
        return passwordField != null && (!weakPassword.contains(passwordField)); //check validation
    }
}
