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
        <article class="${empty sessionScope.username ? 'home-page' : 'page-wrapper'}">

        <article class="task-card">
            <div class="task-elements">
                <span class="task-text">${sessionScope.taskToUpdate.taskName}</span>
                <span class="task-text">${sessionScope.taskToUpdate.taskDescription}</span>
                <c:choose >
                    <c:when test="${!sessionScope.taskToUpdate.taskStatus}">
                        <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                            <input type="hidden" name="taskId" value="${sessionScope.taskToUpdate.taskId}" />
                            <input type="hidden" name="action" value="toggleStatus" />
                            <button type="submit" class="task-buttons" style="all: unset; cursor: pointer;">
                                <img src="images/circle.svg" alt="Toggle Status" />
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${sessionScope.taskToUpdate.taskStatus}">
                        <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                            <input type="hidden" name="taskId" value="${sessionScope.taskToUpdate.taskId}" />
                            <input type="hidden" name="action" value="toggleStatus" />
                            <button type="submit" class="task-buttons" style="all: unset; cursor: pointer;">
                                <img src="images/check_circle.svg" alt="Toggle Status" />
                            </button>
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </article>


        <form action="${pageContext.request.contextPath}/updateTask" method="post" >
            <label for="taskName">Task Name</label>
            <input type="text" name="taskName" id="taskName">

            <label for="taskDescription">Task Description</label>
            <input type="text" name="taskDescription" id="taskDescription">

            <input type="hidden" name="taskId" value="${sessionScope.taskToUpdate.taskId}">

            <button type="submit" name="action" value="update">Update</button>
            <button type="submit" name="action" value="delete">Delete</button>
        </form>

        <%@include file="footer.jsp"%>

        </article>
    </body>
</html>