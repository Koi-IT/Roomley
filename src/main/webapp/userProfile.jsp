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
            <h1>Username</h1>
            <h2>${sessionScope.user.username}</h2>
        </article>

        <article class="welcomeCard">
            <h1>Household Info</h1>
            <c:forEach var="household" items="${sessionScope.households}">
                <div class="task-elements">
                    <p>${household.groupName}</p>
                    <ul>
                        <c:forEach var="member" items="${household.householdMembers}">
                            <li>${member.user.displayName} (${member.role})</li>
                        </c:forEach>
                    </ul>
                    <c:choose>
                        <c:when test="${sessionScope.currentHousehold.householdId == household.householdId}">
                            <p>Current Household</p>
                        </c:when>
                        <c:otherwise>
                            <form action="updateHousehold" method="post">
                                <input type="hidden" name="householdId" value="${household.householdId}" />
                                <button type="submit">Change</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </article>



            <%@include file="footer.jsp"%>

    </body>
</html>
