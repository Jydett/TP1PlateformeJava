package fr.polytech.servlets;

import fr.polytech.exceptions.ServiceException;
import fr.polytech.initializer.ServletInitializer;
import fr.polytech.services.UserService;
import fr.polytech.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RemoveUserServlet", urlPatterns = "/userRemove")
public class RemoveUserServlet extends HttpServlet {
    public static final String MESSAGE_ATTR_KEY = "message";
    public static final String ID_PARAM_KEY = "id";

    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter(ID_PARAM_KEY);
            userService.deleteUser(Long.parseLong(id));
            req.setAttribute(MESSAGE_ATTR_KEY, "Suppression réussie");
        } catch (ServiceException e) {
            req.setAttribute(Constants.ERROR_ATTR_KEY, e.getMessage());
        } catch (Exception ex) {
            req.setAttribute(Constants.ERROR_ATTR_KEY, "Id du mauvais type");
        }
        try {
            req.setAttribute(Constants.ALL_NON_ADMIN_ATTR_KEY, userService.allNonAdminUsers());
            getServletContext().getRequestDispatcher(Constants.CONNECTED_ADMIN_PATH).forward(req, resp);
        } catch (ServiceException e) {
            req.setAttribute(Constants.ERROR_ATTR_KEY, e.getMessage());
            getServletContext().getRequestDispatcher(Constants.INDEX_PATH).forward(req, resp);
        }
    }

    @Override
    public void init() {
        userService = ServletInitializer.getUserService();
    }
}
