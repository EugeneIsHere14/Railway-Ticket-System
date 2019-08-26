<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <%--<script src="js/validation/registration_validation.js"></script>--%>
    <%--<script src="js/validation/station_validation.js"></script>--%>
    <%--<script src="../../../js/validation/rs_validation_edit.js"></script>--%>

    <title>Update Route Station</title>
    <h3 class="center">Edit Route Station</h3>

    <script>
        function showError(container, errorMessage) {
            container.className = 'error';
            var msgElem = document.createElement('span');
            msgElem.style.color ='red';
            msgElem.className = "error-message";
            msgElem.innerHTML = errorMessage;
            container.appendChild(msgElem);
        }

        function removeErrorText(form){
            var errors = form.getElementsByClassName("error-message");
            for (var i = 0; i < errors.length; i++) {
                errors[i].remove()
            }
        }

        function validateEditRouteStation(form) {

            removeErrorText(form);

            var fields = form.getElementsByClassName("field");

            var regExpDate = /^((2018)\-(11|12)\-(0[1-9]|1[0-9]|2[0-9]|3[0-1])\s([0-1]\d|2[0-3])(:[0-5]\d){2}(.?[0-9]?)*)$/;
            var regExpWaitTime = /^([1-9][1-9]?[1-9]?)$/;

            var regExps = [regExpDate, regExpWaitTime, regExpDate];

            for (var i = 0; i < fields.length; i++) {
                if (!fields[i].value) {
                    showError(fields[i].parentNode, ' Enter the ' + fields[i].name + '!');
                    return false;
                }

                var result = regExps[i].test(fields[i].value);

                if (fields[i].value && !result) {
                    showError(fields[i].parentNode, ' ' + fields[i].name +' does not match the pattern!');
                    return false;
                }
            }
            form.submit();
            return true;
        }
    </script>
</head>
<body>
    <p>Hi, <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
    You logged in as ~<c:out value="${userRole}"/>~.</p>
    <p>Please edit fields you need and then press "Edit Route Station" button.</p>
    <form class="right"  action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToAdmin">Back to Admin Page</button>
    </form>
    <form class="center" action="Control" method="post">
        <input type="hidden" name="command" value="updateRouteStation">
        <input type="hidden" name="routeStationId" value="${rs.id}"/>
        <fieldset>
            <legend>Route ID</legend>
            <select name="Route ID">
                <c:forEach var="item" items="${routeIds}">
                    <option>${item}</option>
                </c:forEach>
            </select>
        </fieldset>
        <fieldset>
            <legend>Route Station</legend>
            <select name="Route Station">
                <c:forEach var="item" items="${routeAddName}">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </fieldset>
        <fieldset>
            <legend>Arrival Time</legend>
            <input name="Arrival Time" value="${rs.arrivalTime}" class="field"/>
        </fieldset>
        Date format: yyyy-mm-dd hh:mm:ss
        <fieldset>
            <legend>Waiting Time (min)</legend>
            <input name="Waiting Time" value="${rs.waitTime}" class="field"/>
        </fieldset>
        <fieldset>
            <legend>Departure Time</legend>
            <input name="Departure Time" value="${rs.departTime}" class="field"/>
        </fieldset>
        Date format: yyyy-mm-dd hh:mm:ss<br><br>

        <button class="btn btn-success" type="button" onclick="validateEditRouteStation(this.form)">Edit Route Station</button>
    </form>
</body>
</html>