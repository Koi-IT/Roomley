<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/14/2025
  Time: 6:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%@include file="header.jsp"%>

    <body class="page-wrapper">
        <main class="welcomeCard">
            <article class="welcomeCard">

                <table class="invite-table">
                    <tr>
                        <th>Invited By</th>
                        <th>Invite Status</th>
                        <th>Household Name</th>
                        <th>Accept/Decline</th>
                    </tr>
                    <c:forEach var="invite" items="${sessionScope.invitations}">

                        <tr>
                            <td>${invite}</td>
                            <td>${invite}</td>
                            <td>${invite}</td>
                            <button>Accept</button>
                            <button>Decline</button>
                        </tr>
                    </c:forEach>
                </table>
            </article>

        </main>
    </body>

<%@include file="footer.jsp"%>

</html>
