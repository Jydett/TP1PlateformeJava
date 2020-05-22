package fr.polytech.filters;

import fr.polytech.beans.User;
import fr.polytech.utils.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "connectedAdminFilter")
public class AdminFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Object connected = req.getSession().getAttribute(Constants.CONNECTED_ATTR_KEY);
        if (connected instanceof User && ((User) connected).isAdmin()) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(Constants.CONNECTED_REDIRECT_PATH);
        }
    }
}
