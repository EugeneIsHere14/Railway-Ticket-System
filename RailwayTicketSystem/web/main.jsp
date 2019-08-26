<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <script src="js/validation/registration_validation.js"></script>
    <script src="js/validation/login_validation.js"></script>
    <script src="js/validation/train_validation.js"></script>
    <script src="js/table_visible.js"></script>

    <title>Main Page</title>
    <h2 class="center">Railway Ticket Online Purchasing System</h2>
</head>
<body>
<c:choose>
<c:when test="${user != null}">
    <p> Hi,
        <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
        You logged in as ~<c:out value="${userRole}"/>~. You can buy tickets now.</p>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>
</c:when>
    <c:otherwise>
        <p>To have an option of buying tickets please Log In or Sign Up.</p>
    </c:otherwise>
</c:choose>

<br>
<form class="right" action="Control" method="post">
    <button class="btn btn-dark" type="submit" name="command" value="moveToLog">Login Page</button>
    <br><br>
    <button class="btn btn-dark" type="submit" name="command" value="moveToReg">Registration Page</button>
</form>

<form class="center" action="Control" method="post">
    <fieldset>
        <legend>From</legend>
        <select name="Initial Station">
            <c:forEach var="item" items="${stationName}">
                <option>${item}</option>
            </c:forEach>
        </select>
    </fieldset>
    <fieldset>
        <legend>To</legend>
        <select name="Arrival Station">
            <c:forEach var="item" items="${stationName}">
                <option>${item}</option>
            </c:forEach>
        </select>

    </fieldset>
    <fieldset>
        <legend>Departure Date and Time</legend>
        <input name="Depart Date" class="field"/>
    </fieldset>
    Date format: yyyy-mm-dd
    <br>
    <br>
    <input type="hidden" name="command" value="searchForTrains">
    <button class="btn btn-success" type="button" onclick="validateFindTrains(form)">Search For Trains</button>
</form>
<br>
<br>

<c:if test="${trains != null}">
    <%--<form action="Control" method="post">--%>
    <table class="table table-striped table_width_800 center" border="3">
        <thead>
        <tr>
            <td>Train №</td>
            <td>Departure Station</td>
            <td>Departure Time</td>
            <td>Arrival Station</td>
            <td>Arrival Time</td>
            <td>Way Time (hours)</td>
            <td>Coupe</td>
            <td>Reserved Seat</td>
            <td>Common</td>
            <td>Route</td>
            <c:if test="${user != null}">
                <td>Purchase</td>
            </c:if>
        </tr>
        </thead>
        <c:forEach var="train" items="${trains}">
            <tr>
                <td>${train.trainId}</td>
                <td><input type="hidden" name="init" value="${train.initStation}">${train.initStation}</td>
                <td><input type="hidden" name="departTime" value="${train.departTime}">${train.departTime}</td>
                <td><input type="hidden" name="arrival" value="${train.arrivalStation}">${train.arrivalStation}</td>
                <td><input type="hidden" name="arrivalTime" value="${train.arrivalTime}">${train.arrivalTime}</td>
                <td>${train.wayTime}</td>
                <td>${train.coupe}</td>
                <td>${train.reservedSeat}</td>
                <td>${train.common}</td>
                <td>
                    <form action="Control" method="post">
                        <input type="hidden" name="command" value="moveToWatchRoute">
                        <button class="btn btn-info" type="submit" name="routeId" value="${train.routeId}">Watch
                            Route
                        </button>
                            <%--<input type="hidden" name="routeId" value="${train.routeId}">--%>
                            <%--<button type="submit" name="command" value="moveToWatchRoute">Watch Route</button>--%>
                    </form>
                </td>
                <c:if test="${user != null}">
                    <td>
                        <form action="Control" method="post">
                                <%--<input type="radio" name="Train Id" value="${train.trainId}">--%>
                                <%--<button type="submit" name="command" value="moveToCarriageChoice">Buy Tickets</button>--%>
                            <input type="hidden" name="command" value="moveToCarriageChoice">
                            <button class="btn btn-success" type="submit" name="Train Id" value="${train.trainId}">Buy
                                Tickets
                            </button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>