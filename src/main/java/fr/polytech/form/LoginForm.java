package fr.polytech.form;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class LoginForm {

    public static final String LOGIN_PARAM_KEY = "login";
    public static final String PASSWORD_PARAM_KEY = "password";

    private final String login;
    private final String password;
    protected final List<String> errors;

    public LoginForm(HttpServletRequest req) {
        this.login = req.getParameter(LOGIN_PARAM_KEY);
        this.password = req.getParameter(PASSWORD_PARAM_KEY);
        this.errors = new ArrayList<>();
        validateLogin();
        validatePassword();
    }

    protected void validateLogin() {
        if (login == null) {
            errors.add("Le login ne peut pas être vide");
        }
    }

    protected void validatePassword() {
        if (password == null) {
            errors.add("Le mot de passe ne peut pas être vide");
        }
    }

    public boolean formIsValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
