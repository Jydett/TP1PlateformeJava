<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Administrateur connecté</title>
    <link rel="stylesheet" href="css/error.css">
</head>
<body>
<h1>Tu es connecté administrateur!</h1>

<c:if test="${not empty requestScope.error}">
    <div class="error"> ${requestScope.error}</div>
</c:if>

<c:if test="${not empty requestScope.message}">
    <div class="error message"> ${requestScope.message}</div>
</c:if>

    <form method="post" style="display: inline-block;">
        <table>
            <c:forEach items="${requestScope.allNonAdmin}" var="nonAdmin">
                <tr>
                    <td>
                        ${nonAdmin.login}
                    </td>
                    <td>
                        <input type="submit" formaction="userRemove?id=${nonAdmin.id}" value="Supprimer cet utilisateur">
                    </td>

                </tr>
            </c:forEach>
        </table>
    </form>

    <form method="post" action="login">
        <input type="submit" name="disconnect" value="Se Déconnecter" >
    </form>

</body>
</html>

