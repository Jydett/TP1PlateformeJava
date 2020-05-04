package fr.polytech.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//todo c'est cassé tous les filtres :,(

@WebFilter(filterName = "connectedAdminFilter")
public class AdminFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("connectedAdmin") == null || !((Boolean) req.getSession().getAttribute("connectedAdmin"))) {
            res.sendRedirect("connected.jsp");
        }
        else {
            chain.doFilter(req, res);
        }
    }
}
