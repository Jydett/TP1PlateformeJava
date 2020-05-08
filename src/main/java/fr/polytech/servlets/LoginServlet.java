package fr.polytech.servlets;

import fr.polytech.beans.User;
import fr.polytech.exceptions.ServiceException;
import fr.polytech.form.LoginForm;
import fr.polytech.form.SigInForm;
import fr.polytech.initializer.ServletInitializer;
import fr.polytech.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String disconnect = req.getParameter("disconnect");
        if (disconnect != null) {
            req.getSession().removeAttribute("connected");
            resp.sendRedirect("index.jsp");
            return;
        }
        try {
            User user;
            if (req.getParameter("sigin") != null) {
                SigInForm sigInForm = new SigInForm(req);
                if(sigInForm.formIsValid()) {
                    user = this.userService.createUser(sigInForm);
                }
                else {
                    req.setAttribute("loginError", sigInForm.getError());
                    req.setAttribute("inscription", true);
                    getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                    return;
                }
            }
            else {
                LoginForm loginForm = new LoginForm(req);
                if (loginForm.formIsValid()) {
                    user = userService.authentification(loginForm);
                } else {
                    req.setAttribute("loginError", loginForm.getError());
                    getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                    return;
                }
            }
            try {
                req.getSession().setAttribute("connected", user);
                String redirection = "/connected.jsp";
                if (user.isAdmin()) {
                    redirection = "/connectedAdmin.jsp";
                    req.setAttribute("allNonAdmin", userService.allNonAdminUsers());
                }
                getServletContext().getRequestDispatcher(redirection).forward(req, resp);
            } catch (ServiceException e) {
                req.setAttribute("error", e.getMessage());
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }

        } catch (ServiceException e) {
            req.setAttribute("loginError", e.getMessage());
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirection = req.getParameter("redirection");
        if ("inscription".equals(redirection)) {
            req.setAttribute("inscription", true);
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else if ("admin".equals(redirection)) {
            try {
                req.setAttribute("allNonAdmin", userService.allNonAdminUsers());
                getServletContext().getRequestDispatcher("/connectedAdmin.jsp").forward(req, resp);
            } catch(ServiceException e) {
                req.setAttribute("error", e.getMessage());
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }
    }
    @Override
    public void init() throws ServletException {
        userService = ServletInitializer.USER_SERVICE;
    }

}
