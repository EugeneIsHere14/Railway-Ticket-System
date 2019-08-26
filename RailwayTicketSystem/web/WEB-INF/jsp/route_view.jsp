<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <script src="js/validation/registration_validation.js"></script>
    <script src="js/validation/login_validation.js"></script>

    <title>Route View</title>

    <h2 class="center">Route ${viewRoute.initStation} &mdash; ${viewRoute.arrivalStation} </h2>

</head>
<body>
    <c:if test="${user != null}">
       <p> Hi, <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
        You logged in as ~<c:out value="${userRole}"/>~. You can buy tickets now.</p>
        <form class="right" action="Control" method="post">
            <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
        </form>
    </c:if>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToMain">Back to Main Page</button>
    </form>

    <div>
        <p class="center">Initial Station: &nbsp; &nbsp; &nbsp; &nbsp;${viewRoute.initStation}</p>
        <p class="center">Departure Time:  &nbsp; ${viewRoute.departTime}</p>
    </div><br>

    <p class="center">ROUTE STATIONS</p>

    <table class="table table-striped table_width_800" border="2">
        <thead>
        <tr>
            <td>Route station</td>
            <td>Arrival time</td>
            <td>Waiting time (min)</td>
            <td>Depart time</td>
        </tr>
        </thead>
        <c:forEach var="rs" items="${viewRS}">
            <tr>
                <td>${rs.routeStation}</td>
                <td>${rs.routeStationDepart}</td>
                <td>${rs.waitTime}</td>
                <td>${rs.routeStationArrival}</td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <div>
        <p class="center">Arrival Station: &nbsp; ${viewRoute.arrivalStation}</p>
        <p class="center">Arrival Time: &nbsp; &nbsp; &nbsp;${viewRoute.arrivalTime}</p>
    </div>
</body>
</html>