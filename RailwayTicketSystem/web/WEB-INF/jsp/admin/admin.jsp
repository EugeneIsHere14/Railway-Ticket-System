<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <script src="js/validation/registration_validation.js"></script>
    <script src="js/validation/login_validation.js"></script>
    <script src="js/validation/station_validation.js"></script>
    <script src="js/table_visible.js"></script>

    <title>Admin Page</title>
    <h2 class="center">Admin Page</h2>
</head>
<body>
    <p>Hi, <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
    You logged in as ~<c:out value="${userRole}"/>~.</p>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>

    <p class="center">STATIONS</p>
    <form class="center" action="Control" method="post" id="adminForm">

        <table class="table table-striped table_width_600 center" border="2">
            <thead>
            <tr>
                <td>Station Name</td>
                <td>City</td>
                <td>Region</td>
                <td>Country</td>
                <td>Choice</td>
            </tr>
            </thead>
            <c:forEach var="station" items="${stations}">
                <tr>
                    <td>${station.name}</td>
                    <td>${station.city}</td>
                    <td>${station.region}</td>
                    <td>${station.country}</td>
                    <td><input type="radio" name="stationId" value="${station.id}"></td>
                </tr>
            </c:forEach>
        </table>

        <button class="btn btn-success" type="submit" name="command" value="moveToAddStation">Add Station</button>
        <button class="btn btn-success" type="submit" name="command" value="deleteStation">Delete Station</button>
        <button class="btn btn-success" type="submit" name="command" value="moveToEditStation">Edit Station</button>

    </form>

    <p class="center">ROUTES(${countRoutes})</p>
    <form class="center" action="Control" method="post">

        <table class="table table-striped table_width_800 center" border="2">
            <thead>
            <tr>
                <td>Route ID</td>
                <td>Initial Station</td>
                <td>Depart time</td>
                <td>Destination Station</td>
                <td>Arrival time</td>
                <td>Choice</td>
            </tr>
            </thead>
            <c:forEach var="route" items="${routes}">
                <tr>
                    <td>${route.id}</td>
                    <td>${route.initStation}</td>
                    <td>${route.departTime}</td>
                    <td>${route.arrivalStation}</td>
                    <td>${route.arrivalTime}</td>
                    <td><input type="radio" name="routeId" value="${route.id}"></td>
                </tr>
            </c:forEach>
        </table>

        <button class="btn btn-success" type="submit" name="command" value="moveToAddRoute">Add Route</button>
        <button class="btn btn-success" type="submit" name="command" value="deleteRoute">Delete Route</button>
        <button class="btn btn-success" type="submit" name="command" value="moveToEditRoute">Edit Route</button>
        <button class="btn btn-success" type="submit" name="command" value="getRouteStations">Get Route Stations</button>
        <br>
        <br>

        <c:if test="${rstations != null}">
            <p class="center">ROUTE STATIONS</p>
        <table class="table table-striped table_width_800 center" border="2">
            <thead>
            <tr>
                <td>Route ID</td>
                <td>Route station</td>
                <td>Arrival time</td>
                <td>Waiting time (min)</td>
                <td>Depart time</td>
                <td>Choice</td>
            </tr>
            </thead>
            <c:forEach var="rs" items="${rstations}">
                <tr>
                    <td>${rs.id}</td>
                    <td>${rs.routeStation}</td>
                    <td>${rs.routeStationDepart}</td>
                    <td>${rs.waitTime}</td>
                    <td>${rs.routeStationArrival}</td>
                    <td><input type="radio" name="routeStationId" value="${rs.routeStationId}"></td>
                </tr>
            </c:forEach>
        </table>
            <button class="btn btn-success" type="submit" name="command" value="moveToAddRS">Add Route Station</button>
            <button class="btn btn-success" type="submit" name="command" value="deleteRS">Delete Route Station</button>
            <button class="btn btn-success" type="submit" name="command" value="moveToEditRS">Edit Route Station</button>
        </c:if>
    </form>
</body>
</html>