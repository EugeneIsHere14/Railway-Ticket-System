<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <script src="js/validation/registration_validation.js"></script>
    <script src="js/validation/login_validation.js"></script>
    <script src="js/validation/purchase_validation.js"></script>


    <title>Carriage Choice</title>

    <h2 class="center">Tickets Purchasing</h2>
</head>
<body>
    <p>Hi, <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
    You logged in as ~<c:out value="${userRole}"/>~.</p>
    <p>Please, choose the carriage type, enter tickets quantity you want to buy,<br>
        fill all payment fields and press "Buy Tickets" button!</p>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToMain">Back to Main Page</button>
    </form>
    <br><br>

    <form class="center" action="Control" method="post">
        <table class="table table-striped table_width_800 center" border="2">
            <thead>
            <tr>
                <td>Carriage Type</td>
                <td>Free Seats</td>
                <td>Price (UAH)</td>
                <td>Train Id</td>
                <td>Choice</td>
            </tr>
            </thead>
            <c:forEach var="item" items="${seatsTable}">
                <tr>
                    <td><input type="hidden" name="Carriage Type" value="${item.carriageType}">${item.carriageType}</td>
                    <td><input type="hidden" name="freeSeats" value="${item.freeSeats}">${item.freeSeats}</td>
                    <td>
                        <form action="Control" method="post">
                            <input type="hidden" name="Price" value="${item.price}">${item.price}
                        </form>
                    </td>
                    <td><input type="hidden" name="Train Id" value="${item.trainId}">${item.trainId}</td>
                    <td>
                        <input type="radio" name="choiceTicketType" value="${item.carriageId}">
                    </td>
                </tr>
            </c:forEach>
        </table><br>
        <fieldset>
            <legend>Tickets Quantity</legend>
            <input name="Tickets Quantity" class="field"/>
        </fieldset>
        Must be equal or less than available tickets quantity.
        <br>
        <fieldset>
            <legend>Card Number</legend>
            <input name="Card Number" class="field"/>
        </fieldset>
        Card Number format: xxxx xxxx xxxx xxxx
        <fieldset>
            <legend>Card Expire Date</legend>
            <input name="Card Expire Date" class="field"/>
        </fieldset>
        Card Expire Date format: mm/yy
        <fieldset>
            <legend>CVV</legend>
            <input name="CVV" class="field"/>
        </fieldset>
        CVV format: ddd<br><br>
        <input type="hidden" name="command" value="buyTickets">
        <button class="btn btn-success" type="button" onclick="validatePurchase(this.form)">Buy Tickets</button>
    </form>
</body>
</html>