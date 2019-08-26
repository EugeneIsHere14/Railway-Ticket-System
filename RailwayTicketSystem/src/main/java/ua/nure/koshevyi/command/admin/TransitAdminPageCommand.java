package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class TransitAdminPageCommand extends Command {

    private static final long serialVersionUID = -812300494381387587L;

    private static final Logger LOGGER = Logger.getLogger(TransitAdminPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        LOGGER.trace("Transition to add station page... ");

        HttpSession httpSession = request.getSession();

        List<Station> stations = new StationDAOImpl().getAllStations();
        request.setAttribute("stations", stations);

        List<RouteBean> arrInitStations = new RouteBeanDAOImpl().getInitArrStations();
        request.setAttribute("routes", arrInitStations);

        int routesCount = new RouteBeanDAOImpl().getRoutesCount();
        request.setAttribute("countRoutes", routesCount);

        LOGGER.debug("Commands finished");

        return Path.PAGE_ADMIN;
    }
}