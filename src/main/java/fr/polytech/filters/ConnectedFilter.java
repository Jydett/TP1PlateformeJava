package fr.polytech.filters;

import fr.polytech.utils.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "connectedFilter")
public class ConnectedFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute(Constants.CONNECTED_ATTR_KEY) == null) {
            res.sendRedirect(Constants.INDEX_REDIRECT_PATH);
        } else {
            chain.doFilter(req, res);
        }
    }
}
