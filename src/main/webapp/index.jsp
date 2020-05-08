<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Accueil</title>
        <link rel="stylesheet" href="css/error.css">
    </head>
    <body>


    <c:set var="inscription" value="${not empty requestScope.inscription}" />

    <c:if test="${not empty requestScope.error}">
        <div class="error"> ${requestScope.error}</div>
    </c:if>

    <c:if test="${not empty requestScope.loginError}">
        <c:forEach items="${requestScope.loginError}" var="error">
            <div class="error"> ${error}</div>
        </c:forEach>

    </c:if>
    <form method="POST" name="Form" action="login"
          style="width:50%;margin:auto;background-color:whitesmoke;padding-bottom:15px;">


        <c:choose>
            <c:when test="${inscription}">
                <h2 style="text-align:center;color:black;background-color:wheat;">Page d'inscription</h2>
            </c:when>
            <c:otherwise>
                <h2 style="text-align:center;color:black;background-color:wheat;">Page de connexion</h2>
            </c:otherwise>
        </c:choose>

        <p style="text-align:center;">Pseudonyme : <input required type="text" name="login"  /></p>
        <p style="text-align:center;">Mot de passe : <input required type="password" name="password" /></p>
        <c:if test="${inscription}">
            <p style="text-align:center;">Confirmer mot de passe : <input type="password" name="passwordConfirmation" /></p>
            <p style="text-align:center;">Administrateur : <input type="checkbox" name="admin" /></p>
        </c:if>

        <c:choose>
            <c:when test="${inscription}">
                <p style="width:50%;margin:auto;text-align:center;">
                    <button onclick="window.location='index.jsp'">Retour</button>
                    <input type="submit" name="sigin" value="S'inscrire">
                </p>
            </c:when>
            <c:otherwise>
                <p style="width:50%;margin:auto;text-align:center;">
                    <input type="submit" value="Valider">
                    <button onclick="window.location='login?redirection=inscription'">S'inscrire</button>
                </p>
            </c:otherwise>
        </c:choose>
    </form>
    </body>
</html>
