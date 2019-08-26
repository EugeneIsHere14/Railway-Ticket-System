<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="../../style/css/bootstrap.css">
    <link rel="stylesheet" href="../../style/css/st4.css">
    <%--<script src="../../js/validation/registration_validation.js"></script>--%>
    <%--<script src="../../js/validation/station_validation.js"></script>--%>
    <%--<script src="../../js/validation/rs_validation_add.js"></script>--%>

    <title>Add Route Station</title>
    <h3 class="center">Add Route Station</h3>

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
        function validateRouteStation(form) {

            removeErrorText(form);

            var fields = form.getElementsByClassName("field");

            var regExpDate = /^((2018)\-(11|12)\-(0[1-9]|1[0-9]|2[0-9]|3[0-1])\s([0-1]\d|2[0-3])(:[0-5]\d){2}(.?[0-9]?)*)$/;
            var regExpWaitTime = /^([1-9][1-9]?[1-9]?)$/;

            var regExps = [regExpDate, regExpDate, regExpWaitTime];

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
    <p> <c:choose>
        <c:when test="${routeStationAdd == null}">
            Please, fill all the fields and press 'Add Route Station' button for adding new route station!
        </c:when>
        <c:otherwise>
            Route station was successfully added!
        </c:otherwise>
    </c:choose></p>

    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToAdmin">Back to Admin Page</button>
    </form>

    <form class="center" action="Control" method="post">
        <input type="hidden" name="command" value="addRouteStation">
        <input type="hidden" name="command" value="rsId">
        <fieldset >
            <legend>Route ID</legend>
            <select name="Route ID">
                <c:forEach var="item" items="${routeIds}">
                    <option>${item}</option>
                </c:forEach>
            </select>
        </fieldset>
        <fieldset>
            <legend>Route Station Name</legend>
            <select name="Route Station">
                <c:forEach var="item" items="${routeAddName}">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </fieldset>
        <fieldset>
            <legend>Arrival Time</legend>
            <input name="Arrival Time" class="field"/>
        </fieldset>
        Date format: yyyy-mm-dd hh:mm:ss
        <fieldset>
            <legend>Departure Time</legend>
            <input name="Departure Time" class="field"/>
        </fieldset>
        Date format: yyyy-mm-dd hh:mm:ss
        <fieldset>
            <legend>Waiting Time</legend>
            <input name="Waiting Time" class="field"/>
        </fieldset><br/>

        <button class="btn btn-success" type="button" onclick="validateRouteStation(this.form)">Add Route Station</button>
    </form>
</body>
</html>