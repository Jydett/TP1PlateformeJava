<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lenam
  Date: 04/05/2020
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administrateur connecté</title>
</head>
<body>
    <form method="post" style="display: inline-block;">
        <table>
            <c:forEach items="${requestScope.allNonAdmin}" var="nonAdmin">
                <tr>
                    <td>
                        ${nonAdmin.userName}
                    </td>
                    <td>
                        <input type="submit" formaction="userEdit?action=delete&id=${nonAdmin.id}" value="Supprimer cet utilisateur">
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
