package ua.nure.koshevyi.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteStationDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
import ua.nure.koshevyi.db.entity.Station;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log("Servlet context initialization starts");

        ServletContext servletContext = servletContextEvent.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();
        initI18N(servletContext);

        List<String> stationName = new StationDAOImpl().getAllStationsNames();
        List<Integer> stationIds = new StationDAOImpl().getAllStationId();
        List<Station> stations = new StationDAOImpl().getAllStations();
        List<Integer> routeIds = new RouteDAOImpl().getAllRouteIds();

        servletContext.setAttribute("stationName", stationName);
        servletContext.setAttribute("routeAddName", stations);
        servletContext.setAttribute("routeContext", stationIds);
        servletContext.setAttribute("routeIds", routeIds);

        log("Servlet context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log("Servlet context destruction starts");

        log("Servlet context destruction finished");
    }

    private void initI18N(ServletContext servletContext) {
        LOGGER.debug("I18N subsystem initialization started");
        servletContext.setInitParameter("locales", "en");

        String localesValue = servletContext.getInitParameter("locales");
        if (localesValue == null || localesValue.isEmpty()) {
            LOGGER.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }

            LOGGER.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }

        LOGGER.debug("I18N subsystem initialization finished");
    }

    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext.getRealPath(
                    "WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log("Log4J initialization finished");
    }

    private void initCommandContainer() {
        LOGGER.debug("Command container initialization started");

        try {
            Class.forName("ua.nure.koshevyi.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        LOGGER.debug("Command container initialization finished");
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }
}