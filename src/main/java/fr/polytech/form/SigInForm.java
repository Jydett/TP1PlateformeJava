package fr.polytech.form;

import javax.servlet.http.HttpServletRequest;

public class SigInForm extends LoginForm {
    private boolean isAdmin;
    private String isAdminStr;
    private String validatePassowrd;

    public SigInForm(HttpServletRequest req) {
        super(req);
        this.isAdminStr = req.getParameter("admin");
        this.validatePassowrd = req.getParameter("passwordConfirmation");
        validateValidatePassword();
        validateIsAdmin();

    }

    protected void validatePassword() {
        String password = getPassword();
        if (password != null) {
            if (password.length() < 8) {
                error.add("le mot de passe doit contenir au moins 8 caractères");
            }
            if (password.length() > 16) {
                error.add("le mot de passe ne doit pas faire plus de 16 caractères");
            }
        }
    }

    protected void validateLogin() {
        String login = getLogin();
        if (login != null) {
            if (login.length() < 3) {
                error.add("le login doit contenir au moins 3 caractères");
            }
            if (login.length() > 10) {
                error.add("le login ne doit pas faire plus de 10 caractères");
            }
        }
    }

    private void validateValidatePassword() {
        if (this.validatePassowrd == null) {
            error.add("merci de confirmer le mot de passe");
        }
        else if (!this.validatePassowrd.equals(super.getPassword())) {
            error.add("le mot de passe de confirmation n'est pas le même que le mot de passe");
        }
    }

    private void validateIsAdmin() {
        try {
            this.isAdmin = Boolean.parseBoolean(isAdminStr);
        } catch (Exception e){
            error.add("le paramètre isAdmin doit être un booléen");
        }
    }

    public String getValidatePassowrd() {
        return validatePassowrd;
    }

    public boolean isAdmin() {
        return isAdmin;
    }


}
