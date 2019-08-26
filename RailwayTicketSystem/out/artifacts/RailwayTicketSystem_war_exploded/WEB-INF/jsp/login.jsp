<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <c:set var="title" value="Login"/>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="../../style/css/bootstrap.css">
    <link rel="stylesheet" href="../../style/css/st4.css">
    <script src="../../js/validation/registration_validation.js"></script>
    <script src="../../js/validation/login_validation.js"></script>

    <h2 class="center">Login Page</h2>
</head>
<body>


<p>
    <c:choose>
    <c:when test="${user != null}">
    Hi,
        <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>!
    You logged in as ~<c:out value="${userRole}"/>~. You can buy tickets now.
<form class="right" action="Control" method="post">
    <button class="btn btn-dark" type="submit" name="command" value="logoutLoginPage">Log Out</button>
</form>
</c:when>
<c:otherwise>Please Log In or Register for further work with system!</c:otherwise>
</c:choose></p>

<form class="right" action="Control" method="post">
    <button class="btn btn-dark" type="submit" name="command" value="moveToMain">Back to Main Page</button>
</form>
<form class="right" action="Control" method="post">
    <button class="btn btn-dark" type="submit" name="command" value="moveToReg">Registration</button>
</form>
<form class="center" id="login_form" action="Control" method="post">

    <input type="hidden" name="command" value="login"/>

    <fieldset>
        <legend>Login</legend>
        <input name="Login" class="field"/>
    </fieldset>
    <br>
    <fieldset>
        <legend>Password</legend>
        <input type="password" name="Password" class="field"/>
    </fieldset>
    <br/>

    <button class="btn btn-success" type="button" onclick="validateLogin(this.form)">Log In</button>
</form>

</body>
</html>