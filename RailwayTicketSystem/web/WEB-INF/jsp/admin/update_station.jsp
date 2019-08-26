<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="style/css/bootstrap.css">
    <link rel="stylesheet" href="style/css/st4.css">
    <%--<script src="js/validation/registration_validation.js"></script>--%>
    <%--<script src="js/validation/station_validation.js"></script>--%>

    <title>Update Station</title>
    <h3 class="center">Edit Station</h3>

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
    <p>Please edit fields you need and then press "Edit Station" button.</p>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="logoutMainPage">Log Out</button>
    </form>
    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToAdmin">Back to Admin Page</button>
    </form>
    <form class="center" action="Control" method="post">
        <input type="hidden" name="command" value="updateStation">
        <input type="hidden" name="Station Id" value="${station.id}"/>
        <fieldset >
            <legend>Station Name</legend>
            <input name="Station Name" value="${station.name}" class="field"/>
        </fieldset>
        <fieldset>
            <legend>City</legend>
            <input name="City" value="${station.city}" class="field"/>
        </fieldset>
        <fieldset>
            <legend>Region</legend>
            <input name="Region" value="${station.region}" class="field"/>
        </fieldset>
        <fieldset>
            <legend>Country</legend>
            <input name="Country" value="${station.country}" class="field"/>
        </fieldset><br/>
        <button class="btn btn-success" type="button" onclick="validateStation(this.form)">Edit Station</button>
    </form>
</body>
</html>