package fr.polytech.servlets;

import fr.polytech.exceptions.ServiceException;
import fr.polytech.initializer.ServletInitializer;
import fr.polytech.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RemoveUserServlet", urlPatterns = "/userRemove")
public class RemoveUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            userService.deleteUser(Long.parseLong(id));
            req.setAttribute("message", "suppression réussie");
        } catch(ServiceException e) {
            req.setAttribute("error", e.getMessage());
        } catch(Exception ex) {
            req.setAttribute("error", "id du mauvais type");
        }
        try {
            req.setAttribute("allNonAdmin", userService.allNonAdminUsers());
            getServletContext().getRequestDispatcher("/connectedAdmin.jsp").forward(req, resp);
        } catch(ServiceException e) {
            req.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        userService = ServletInitializer.USER_SERVICE;
    }
}
