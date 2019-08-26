package ua.nure.koshevyi.command;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.admin.*;
import ua.nure.koshevyi.command.common.*;
import ua.nure.koshevyi.command.user.BuyTicketsCommand;
import ua.nure.koshevyi.command.user.TransitTicketPurchasePageCommand;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger LOGGER = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<>();

    static {
        // common command
        commands.put("login", new LoginCommand());
        commands.put("logoutMainPage", new LogoutMainPageCommand());
        commands.put("logoutLoginPage", new LogoutLoginPageCommand());
        commands.put("logoutRegPage", new LogoutRegPageCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("moveToLog", new TransitLoginPageCommand());
        commands.put("moveToReg", new TransitRegPageCommand());
        commands.put("moveToMain", new TransitMainPageCommand());
        commands.put("searchForTrains", new FindTrainsCommand());
        commands.put("moveToWatchRoute", new TransitWatchRoutePageCommand());
        commands.put("noCommand", new NoCommand());
//        command.put("viewSettings", new ViewSettingsCommand());

        // user command
        commands.put("moveToCarriageChoice", new TransitTicketPurchasePageCommand());
        commands.put("buyTickets", new BuyTicketsCommand());

        // admin command
        commands.put("moveToAddStation", new TransitAddStationPageCommand());
        commands.put("moveToAdmin", new TransitAdminPageCommand());
        commands.put("addStation", new StationAddCommand());
        commands.put("deleteStation", new StationDeleteCommand());
        commands.put("moveToEditStation", new TransitUpdStationPageCommand());
        commands.put("updateStation", new StationUpdateCommand());
        commands.put("getRouteStations", new RouteStationsGetCommand());
        commands.put("moveToAddRoute", new TransitAddRoutePageCommand());
        commands.put("addRoute", new RouteAddCommand());
        commands.put("deleteRoute", new RouteDeleteCommand());
        commands.put("updateRoute", new RouteUpdateCommand());
        commands.put("moveToEditRoute", new TransitUpdRoutePageCommand());
        commands.put("moveToAddRS", new TransitAddRouteStPageCommand());
        commands.put("addRouteStation", new RouteStationAddCommand());
        commands.put("deleteRS", new RouteStationDeleteCommand());
        commands.put("moveToEditRS", new TransitUpdRouteStPageCommand());
        commands.put("updateRouteStation", new RouteStationUpdateCommand());

        LOGGER.debug("Command container was successfully initialized");
        LOGGER.trace("Number of command --> " + commands.size());
        LOGGER.trace("Commands key / value --> " + commands);
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOGGER.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}