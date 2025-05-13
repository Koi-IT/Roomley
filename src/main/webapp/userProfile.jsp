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
        <article class="${empty sessionScope.username ? 'home-page' : 'page-wrapper'}">

                <c:forEach var="user" items="${sessionScope.user}">
                    <c:if test="${empty user.username}">

                        <article class="task-card">
                            <div class="task-elements">
                                <span class="task-text">${user.username}</span>
                                <span class="task-text">${user.u}</span>
                                <a href="editTask?taskId=${user.taskId}" class="edit-button">Edit</a>
                                <a href="updateTask?taskId=${user.taskId}" class="task-buttons">

                                    <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                                        <input type="hidden" name="taskId" value="${task.taskId}" />
                                        <input type="hidden" name="action" value="toggleStatus" />
                                        <button type="submit" class="task-buttons" style="all: unset; cursor: pointer;">
                                            <img src="images/circle.svg" alt="Toggle Status" />
                                        </button>
                                    </form>

                                </a>
                            </div>
                        </article>

                    </c:if>
                </c:forEach>

            <%@include file="footer.jsp"%>

        </article>
    </body>
</html>
