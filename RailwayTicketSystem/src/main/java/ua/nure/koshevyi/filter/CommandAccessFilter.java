package ua.nure.koshevyi.filter;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.util.Role;
import ua.nure.koshevyi.util.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class CommandAccessFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CommandAccessFilter.class);

    private static Map<String, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    public void destroy() {
        LOGGER.debug("Filter destruction starts");
        LOGGER.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        LOGGER.debug("Filter starts");

        if (accessAllowed(request)) {
            LOGGER.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";

            request.setAttribute("errorMessage", errorMessage);
            LOGGER.trace("Set the request attribute: errorMessage --> " + errorMessage);

            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty())
            return false;

        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null)
            return false;

        Role userRole = (Role)session.getAttribute("userRole");
        if (userRole == null)
            return false;

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        LOGGER.debug("Filter initialization starts");

        // roles
        accessMap.put("admin", asList(fConfig.getInitParameter("admin")));
        accessMap.put("user", asList(fConfig.getInitParameter("user")));
        LOGGER.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("common"));
        LOGGER.trace("Common command --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        LOGGER.trace("Out of control command --> " + outOfControl);

        LOGGER.debug("Filter initialization finished");
    }

    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }
}