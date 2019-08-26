package ua.nure.koshevyi.command.common;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.TrainRouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.TrainRouteBeanDAOImpl;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FindTrainsCommand extends Command {

    private static final long serialVersionUID = 4500167243915909407L;

    private static final Logger LOGGER = Logger.getLogger(FindTrainsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        HttpSession session = request.getSession();

        String initStation = request.getParameter("Initial Station");
        String arrivalStation = request.getParameter("Arrival Station");
        String departDate = request.getParameter("Depart Date");

        if ((initStation != null && arrivalStation != null) && (!initStation.equals(arrivalStation))) {

            List<TrainRouteBean> beanList = new TrainRouteBeanDAOImpl()
                    .getAvailableTrainRouteViews(initStation, arrivalStation, departDate);

            LOGGER.trace("Bean list  " + beanList);

            session.setAttribute("trains", beanList);

            LOGGER.trace("Train(s) displayed ");

            forward =  Path.PAGE_MAIN;
        } else {
            errorMessage = "You have not chosen init / arrival station(s)" +
                    " to find trains or chosen the same!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");

        return forward;
    }
}