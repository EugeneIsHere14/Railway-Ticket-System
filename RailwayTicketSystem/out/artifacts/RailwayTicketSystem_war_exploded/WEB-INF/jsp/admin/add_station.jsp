<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <%--<script src="js/validation/registration_validation.js"></script>--%>
    <%--<script src="js/validation/station_validation.js"></script>--%>
    <%--<script src="js/table_visible.js"></script>--%>

    <title>Add Station</title>
    <h3 class="center">Add Station</h3>

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
        function validateStation(form) {

            removeErrorText(form);

            var fields = form.getElementsByClassName("field");

            var regExpName = /^(\b[A-Z]{1}[a-zA-Zа-яА-Я0-9_\-\s]{2,}[a-zA-Zа-яА-Я0-9])$/;
            // var regExpCity = /^([a-zA-Zа-яА-Я_\-\s]){1,}[a-zA-Zа-яА-Я])$/;
            var regExpCity = /^(\b[A-Z]{1}[a-zA-Zа-яА-Я\-\s]{2,}[a-zA-Zа-яА-Я])$/;
            var regExpRegion = /^([a-zA-Zа-яА-Я_\-\s]{1,}[a-zA-Zа-яА-Я])$/;
            var regExpCountry = /^([a-zA-Zа-яА-Я_\-\s]{1,}[a-zA-Zа-яА-Я])$/;

            var regExps = [regExpName, regExpCity, regExpRegion, regExpCountry];

            for (var i = 0; i < fields.length; i++) {
                if (!fields[i].value) {
                    showError(fields[i].parentNode, ' Enter the ' + fields[i].name + '!');
                    return false;
                }

                var result = regExps[i].test(fields[i].value);

                if (!result) {
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
    <p><c:choose>
        <c:when test="${station == null}">
            Please, fill all the fields and press 'Add Station' button for adding new station!
        </c:when>
        <c:otherwise>
            Station was successfully added!
        </c:otherwise>
    </c:choose></p>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToAdmin">Back to Admin Page</button>
    </form>

    <form class="center" action="Control" method="post">
        <input type="hidden" name="command" value="addStation">
        <fieldset >
            <legend>Station Name</legend>
            <input name="Station Name" class="field"/>
        </fieldset>
        <fieldset>
            <legend>City</legend>
            <input name="City" class="field"/>
        </fieldset>
        <fieldset>
            <legend>Region</legend>
            <input name="Region" class="field"/>
        </fieldset>
        <fieldset>
            <legend>Country</legend>
            <input name="Country" class="field"/>
        </fieldset><br/>
        <button class="btn btn-success" type="button" onclick="validateStation(this.form)">Add Station</button>
        <%--<button class="btn btn-success" type="submit">Add Station</button>--%>
    </form>

</body>
</html>