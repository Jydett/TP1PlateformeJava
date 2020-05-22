package fr.polytech.form;

import javax.servlet.http.HttpServletRequest;

public class SigInForm extends LoginForm {
    public static final String ADMIN_PARAM_KEY = "admin";
    public static final String PASSWORD_CONFIRMATION_PARAM_KEY = "passwordConfirmation";
    public static final int MIN_LENGTH_PASSWORD = 8;
    public static final int MAX_LENGTH_PASSWORD = 16;
    public static final int MIN_LENGTH_LOGIN = 3;
    public static final int MAX_LENGTH_LOGIN = 10;

    private boolean isAdmin;
    private final String isAdminStr;
    private final String validatePassowrd;

    public SigInForm(HttpServletRequest req) {
        super(req);
        this.isAdminStr = req.getParameter(ADMIN_PARAM_KEY);
        this.validatePassowrd = req.getParameter(PASSWORD_CONFIRMATION_PARAM_KEY);
        validateValidatePassword();
        validateIsAdmin();
    }

    protected void validatePassword() {
        String password = getPassword();
        if (password != null) {
            if (password.length() < MIN_LENGTH_PASSWORD) {
                errors.add("Le mot de passe doit contenir au moins 8 caractères");
            }
            if (password.length() > MAX_LENGTH_PASSWORD) {
                errors.add("Le mot de passe ne doit pas faire plus de 16 caractères");
            }
        }
    }

    protected void validateLogin() {
        String login = getLogin();
        if (login != null) {
            if (login.length() < MIN_LENGTH_LOGIN) {
                errors.add("Le login doit contenir au moins 3 caractères");
            }
            if (login.length() > MAX_LENGTH_LOGIN) {
                errors.add("Le login ne doit pas faire plus de 10 caractères");
            }
        }
    }

    private void validateValidatePassword() {
        if (this.validatePassowrd == null) {
            errors.add("Merci de confirmer le mot de passe");
        }
        else if (!this.validatePassowrd.equals(super.getPassword())) {
            errors.add("Le mot de passe de confirmation n'est pas le même que le mot de passe");
        }
    }

    private void validateIsAdmin() {
        try {
            this.isAdmin = Boolean.parseBoolean(isAdminStr);
        } catch (Exception e){
            errors.add("Le paramètre isAdmin doit être un booléen");
        }
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
