package ua.nure.koshevyi.command.common;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.bean.TrainRouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.TrainRouteBeanDAOImpl;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TransitWatchRoutePageCommand extends Command {

    private static final long serialVersionUID = -4077901692105814317L;

    private static final Logger LOGGER = Logger.getLogger(TransitWatchRoutePageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String routeId = request.getParameter("routeId");
        String trainId = request.getParameter("Train Id");
        LOGGER.trace("Train id = "  +trainId);

        TrainRouteBean bean = new TrainRouteBeanDAOImpl().read(Integer.parseInt(routeId));

        List<RouteBean> list = new RouteBeanDAOImpl().getAllRStationsByRouteId(Integer.parseInt(routeId));

        request.setAttribute("viewRoute", bean);

        request.setAttribute("viewRS", list);

        LOGGER.trace("Transition to route view page... ");

        LOGGER.debug("Commands finished");

        return Path.PAGE_ROUTE_VIEW;
    }
}