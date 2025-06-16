<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/13/2025
  Time: 9:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<!DOCTYPE html>
<html lang="en">

    <%@include file="header.jsp"%>

    <body class="page-wrapper">

        <article class="welcomeCard">
            <h1>User Info</h1>
            <form action="" method="post">
                <label for="username"> Username: </label>
                <input id="username" type="text" value="${sessionScope.username}">

                <button type="submit">Change</button>

            </form>


        </article>
        <article class="welcomeCard">
            <h1>Household Info</h1>
                <c:forEach var="household" items="${sessionScope.households}">
                    <div class="task-elements">
                    <p>${household.groupName}</p>
                        <c:choose>
                            <c:when test="${sessionScope.currentHousehold.householdId == household.householdId}">
                                <p>Current Household</p>
                            </c:when>
                            <c:otherwise>
                                <a href="updateHousehold">Change</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
        </article>


            <%@include file="footer.jsp"%>

    </body>
</html>
