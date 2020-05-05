package fr.polytech.servlets;

import fr.polytech.beans.User;
import fr.polytech.exceptions.ServiceException;
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
            req.getSession().removeAttribute("isAdmin");
            resp.sendRedirect("index.jsp");
            return;
        }
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            User user = userService.authentification(login, password);
            req.getSession().setAttribute("connected", user);
            req.getSession().setAttribute("isAdmin", user.isAdmin());
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

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("inscription", true);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        userService = ServletInitializer.USER_SERVICE;
    }
}
