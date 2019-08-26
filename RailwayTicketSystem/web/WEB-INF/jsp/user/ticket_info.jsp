<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <script src="js/validation/registration_validation.js"></script>
    <script src="js/validation/login_validation.js"></script>
    <script src="js/validation/train_validation.js"></script>

    <title>Ticket Information</title>
    <h2 class="center">Information about purchased tickets</h2>
</head>
<body>
        <p>Hi, <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
        You logged in as ~<c:out value="${userRole}"/>~. <br>
        You have successfully purchased ticket(s). There is information of it below.</p>
        <form class="right" action="Control" method="post">
            <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
        </form>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToMain">Back to Main Page</button>
    </form><br>



    <table class="table table-striped table_width_600 center" border="2">
        <tr>
            <td>First name:</td>
            <td>${ticketInfo.firstName}</td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td>${ticketInfo.lastName}</td>
        </tr>
        <tr>
            <td>Train number:</td>
            <td>${ticketInfo.trainId}</td>
        </tr>
        <tr>
            <td>Carriage type:</td>
            <td>${ticketInfo.carriageType}</td>
        </tr>
        <tr>
            <td>Departure station:</td>
            <td>${ticketInfo.initStation}</td>
        </tr>
        <tr>
            <td>Departure time:</td>
            <td>${ticketInfo.departDate}</td>
        </tr>
        <tr>
            <td>Arrival station:</td>
            <td>${ticketInfo.arrivalStation}</td>
        </tr>
        <tr>
            <td>Arrival time:</td>
            <td>${ticketInfo.arrivalDate}</td>
        </tr>
        <tr>
            <td>Tickets quantity:</td>
            <td>${ticketsQuantity}</td>
        </tr>
        <tr>
            <td>Total cost:</td>
            <td>${ticketInfo.price}</td>
        </tr>
    </table>

        <br><br>
</body>
</html>