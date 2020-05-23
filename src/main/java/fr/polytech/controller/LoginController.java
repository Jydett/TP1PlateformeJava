package fr.polytech.controller;

import fr.polytech.beans.User;
import fr.polytech.exceptions.ServiceException;
import fr.polytech.form.LoginForm;
import fr.polytech.form.SigInForm;
import fr.polytech.initializer.ServletInitializer;
import fr.polytech.services.UserService;
import fr.polytech.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    public static final String LOGIN_ERROR_ATTR_KEY = "loginError";
    public static final String INSCRIPTION_ATTR_KEY = "inscription";
    public static final String ADMIN_REDIRECTION_KEY = "admin";

    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String disconnect = req.getParameter(Constants.DISCONNECT_ATTR_KEY);
        if (disconnect != null) {
            req.getSession().removeAttribute(Constants.CONNECTED_ATTR_KEY);
            resp.sendRedirect(Constants.INDEX_REDIRECT_PATH);
            return;
        }
        boolean sigin = req.getParameter(Constants.SIGIN_ATTR_KEY) != null;
        try {
            User user;
            if (sigin) {
                SigInForm sigInForm = new SigInForm(req);
                if (sigInForm.formIsValid()) {
                    user = this.userService.createUser(sigInForm);
                } else {
                    req.setAttribute(LOGIN_ERROR_ATTR_KEY, sigInForm.getErrors());
                    req.setAttribute(INSCRIPTION_ATTR_KEY, true);
                    getServletContext().getRequestDispatcher(Constants.INDEX_PATH).forward(req, resp);
                    return;
                }
            } else {
                LoginForm loginForm = new LoginForm(req);
                if (loginForm.formIsValid()) {
                    user = userService.authentification(loginForm);
                } else {
                    req.setAttribute(LOGIN_ERROR_ATTR_KEY, loginForm.getErrors());
                    getServletContext().getRequestDispatcher(Constants.INDEX_PATH).forward(req, resp);
                    return;
                }
            }
            try {
                req.getSession().setAttribute(Constants.CONNECTED_ATTR_KEY, user);
                String redirection = Constants.CONNECTED_PATH;
                if (user.isAdmin()) {
                    redirection = Constants.CONNECTED_ADMIN_PATH;
                    req.setAttribute(Constants.ALL_NON_ADMIN_ATTR_KEY, userService.allNonAdminUsers());
                }
                getServletContext().getRequestDispatcher(redirection).forward(req, resp);
            } catch (ServiceException e) {
                req.setAttribute(Constants.ERROR_ATTR_KEY, e.getMessage());
                getServletContext().getRequestDispatcher(Constants.INDEX_PATH).forward(req, resp);
            }
        } catch (ServiceException e) {
            req.setAttribute(LOGIN_ERROR_ATTR_KEY, e.getMessage());
            if (sigin) {
                req.setAttribute(INSCRIPTION_ATTR_KEY, true);
            }
            getServletContext().getRequestDispatcher(Constants.INDEX_PATH).forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirection = req.getParameter(Constants.REDIRECTION_ATTR_KEY);
        if (INSCRIPTION_ATTR_KEY.equals(redirection)) {
            req.setAttribute(INSCRIPTION_ATTR_KEY, true);
            getServletContext().getRequestDispatcher(Constants.INDEX_PATH).forward(req, resp);
        } else if (ADMIN_REDIRECTION_KEY.equals(redirection)) {
            try {
                req.setAttribute(Constants.ALL_NON_ADMIN_ATTR_KEY, userService.allNonAdminUsers());
                getServletContext().getRequestDispatcher(Constants.CONNECTED_ADMIN_PATH).forward(req, resp);
            } catch (ServiceException e) {
                req.setAttribute(Constants.ERROR_ATTR_KEY, e.getMessage());
                getServletContext().getRequestDispatcher(Constants.INDEX_PATH).forward(req, resp);
            }
        }
    }
    @Override
    public void init() {
        userService = ServletInitializer.getUserService();
    }
}
