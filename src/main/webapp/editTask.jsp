<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/6/2025
  Time: 3:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="title" value="Edit Task"/>
<!DOCTYPE html>
<html lang="en">

    <%@include file="header.jsp"%>

    <body class="page-wrapper">
        <article class="${not empty sessionScope.username ? 'page-wrapper' : 'home-page'}">

        <article class="welcomeCard">
            <div class="task-elements">
                <span class="task-text">Task Name: ${sessionScope.taskToUpdate.taskName}</span>
                <span class="task-text">Task Description: ${sessionScope.taskToUpdate.taskDescription}</span>
                <c:choose >
                    <c:when test="${!sessionScope.taskToUpdate.taskStatus}">
                        <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                            <input type="hidden" name="taskId" value="${sessionScope.taskToUpdate.taskId}" />
                            <input type="hidden" name="action" value="toggleStatus" />
                        </form>
                    </c:when>
                    <c:when test="${sessionScope.taskToUpdate.taskStatus}">
                        <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                            <input type="hidden" name="taskId" value="${sessionScope.taskToUpdate.taskId}" />
                            <input type="hidden" name="action" value="toggleStatus" />
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </article>

    <article class="welcomeCard">
        <form action="${pageContext.request.contextPath}/updateTask" method="post" >
            <label for="taskName">Task Name</label>
            <input type="text" name="taskName" id="taskName" value="${sessionScope.taskToUpdate.taskName}" required>

            <label for="taskDescription">Task Description</label>
            <input type="text" name="taskDescription" id="taskDescription" value="${sessionScope.taskToUpdate.taskDescription}" required>

            <label for="assignedUser">Reassign User:</label>
            <select name="assignedUser" id="assignedUser">
                <c:forEach var="user" items="${householdUsers}">
                    <option value="${user.username}">${user.username}</option>
                </c:forEach>
            </select>

            <input type="hidden" name="taskId" value="${sessionScope.taskToUpdate.taskId}">

            <button type="submit" name="action" value="update">Update</button>
        </form>
    </article>
        <%@include file="footer.jsp"%>

        </article>
    </body>
</html>