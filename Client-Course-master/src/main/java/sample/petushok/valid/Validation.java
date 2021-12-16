package sample.petushok.valid;

import sample.petushok.controller.constants.ConstParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static Validation validation = new Validation();
    private Validation(){}

    public boolean validEmailAndPassword(String login ,String password){
        if(!login.equals("") && !password.equals("")){
            return true;
        }
        return false;
    }

    public boolean validEmailAndPasswordFromRegistration(String email,String password){
        Pattern pattern = Pattern.compile(ConstParams.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);


        if(matcher.matches() || password.length()>3){
            return true;
        }else {
            return false;
        }
    }
}
