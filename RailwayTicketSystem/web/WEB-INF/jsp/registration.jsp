<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <c:set var="title" value="Registration" />
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="../../style/css/bootstrap.css">
    <link rel="stylesheet" href="../../style/css/st4.css">
    <script src="../../js/validation/registration_validation.js"></script>

    <h2 class="center">Registration Page</h2>
</head>
<body>

    <c:if test="${user != null}">
        <p>Hi, <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
            You logged in as ~<c:out value="${userRole}"/>~. You can buy tickets now.</p>
        <form class="right" action="Control" method="post">
            <button class="btn btn-dark" type="submit" name="command" value="logoutRegPage">Log Out</button>
        </form>
    </c:if>

    <p>Please, fill all the fields and press 'Submit' button for registration!</p>

    <form class="right" action="Control" method="post">
        <button class="btn btn-dark" type="submit" name="command" value="moveToMain">Back to Main Page</button>
    </form>

    <form action="Control" method="post" class="center">
        <input class="form-control" type="hidden" name="command" value="registration">
        <fieldset >
            <legend>Login</legend>
            <input name="Login" class="field"/>
        </fieldset>
        Must starts with letter.
        <fieldset>
            <legend>Password</legend>
            <input type="password" name="Password" class="field"/>
        </fieldset>
        Must starts with letter or digit.
        <fieldset>
            <legend>Confirm Password</legend>
            <input type="password" name="Password Confirmation" class="field"/>
        </fieldset>
        Password confirmation.
        <fieldset>
            <legend>First Name</legend>
            <input name="First Name" class="field"/>
        </fieldset>
        Must contains letters only.
        <fieldset>
            <legend>Last Name</legend>
            <input name="Last Name" class="field"/>
        </fieldset>
        Must contains letters only.<br/>
        <button class="btn btn-success" type="button" onclick="validate(this.form)">Submit</button>
    </form>
</body>
</html>