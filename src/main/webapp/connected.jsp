<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Connecté</title>
</head>
<body>
    <jsp:useBean id="connected" scope="session" type="fr.polytech.beans.User"/>

    <h1>Tu es connecté!</h1>

    <form method="post" action="login">
        <input type="submit" name="disconnect" value="Se Déconnecter" >
        <c:if test="${connected.admin}">
            <button onclick="window.location='login?redirection=admin'">Accéder à la page administrateur</button>
        </c:if>
    </form>
</body>
</html>
