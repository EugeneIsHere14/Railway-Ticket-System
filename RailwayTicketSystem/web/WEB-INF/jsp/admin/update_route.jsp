<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <script src="js/validation/registration_validation.js"></script>
    <script src="js/validation/station_validation.js"></script>
    <script src="../../../js/validation/route_validation.js"></script>

    <title>Update Route</title>
    <h3 class="center">Edit Route</h3>
</head>
<body>
    <p>Hi, <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
    You logged in as ~<c:out value="${userRole}"/>~.</p>
    <p>Please edit fields you need and then press "Edit Route" button.</p>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToAdmin">Back to Admin Page</button>
    </form>
    <form class="center" action="Control" method="post">
        <input type="hidden" name="command" value="updateRoute">
        <input type="hidden" name="Route Id" value="${route.id}"/>
        <fieldset >
            <legend>Initial Station</legend>
            <select name="Initial Station">
                <c:forEach var="item" items="${routeAddName}">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </fieldset>
        <fieldset>
            <legend>Departure Time</legend>
            <input name="Departure Time" value="${route.departTime}" class="field"/>
        </fieldset>
        Date format: yyyy-mm-dd hh:mm:ss
        <fieldset>
            <legend>Arrival Station</legend>
            <select name="Arrival Station">
                <c:forEach var="item" items="${routeAddName}">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </fieldset>
        <fieldset>
            <legend>Arrival Time</legend>
            <input name="Arrival Time" value="${route.arrivalTime}" class="field"/>
        </fieldset>
        Date format: yyyy-mm-dd hh:mm:ss<br><br>

        <button class="btn btn-success" type="button" onclick="validateRoute(this.form)">Edit Route</button>
    </form>
</body>
</html>