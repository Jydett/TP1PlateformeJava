package fr.polytech.form;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class LoginForm {

    private String login;
    private String password;
    protected List<String> error;

    public LoginForm(HttpServletRequest req) {
        this.login = req.getParameter("login");
        this.password = req.getParameter("password");
        this.error = new ArrayList<>();
        validateLogin();
        validatePassword();
    }

    protected void validateLogin() {
        if(login == null) {
            error.add("le login ne peut pas être vide");
        }
    }

    protected void validatePassword() {
        if(password == null) {
            error.add("le mot de passe ne peut pas être vide");
        }
    }

    public boolean formIsValid() {
        return error.isEmpty();
    }

    public List<String> getError() {
        return error;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


}
