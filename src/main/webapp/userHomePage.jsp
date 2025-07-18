<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/5/2025
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="title" value="Welcome!"/>
<!DOCTYPE html>
<html lang="en">

    <%@include file="header.jsp"%>

    <body class="page-wrapper container">
        <div class="page-wrapper">

            <div class="grid">
                <article class="userHomePageCard">
                    <form action="distributeTasks" method="get" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                        <input type="hidden" name="username" value="${sessionScope.user.username}">
                        <button type="submit">Distribute Tasks</button>
                    </form>
                </article>
                <article class="userHomePageCard">
                    <h3 class="dm-serif-text-regular-italic">Welcome</h3>
                    <h3 class="dm-serif-text-regular-italic">${sessionScope.user.username}</h3>
                </article>
                <article class="userHomePageCard">
                    <form action="householdCreation.jsp" method="get" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                        <button type="submit">Create Household</button>
                    </form>
                </article>
            </div>

            <!-- Task Buttons Section -->
            <div class="grid">
                <article class="task-title new-task-button">
                    <h3>Tasks to-do</h3>
                    <a href="${pageContext.request.contextPath}/taskCreateLink?householdId=8">
                        <button>New Task<img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="Add New Task"></button>
                    </a>
                </article>
                <article class="task-title"><h3>Assigned to me</h3></article>
                <article class="task-title"><h3>Finished tasks</h3></article>
            </div>

            <!-- Task Columns Section -->
            <div class="task-grid">

                <!-- To-Do Tasks Column -->
                <div class="task-column col-4">
                    <c:forEach var="task" items="${sessionScope.tasks}">
                        <c:if test="${!task.taskStatus}">
                            <article class="task-card">
                                <div class="task-elements">
                                    <span class="task-text">${task.taskName}</span>
                                    <span class="task-text">${task.taskDescription}</span>
                                    <a href="deleteTaskLink?taskId=${task.taskId}">Delete</a>
                                    <a href="taskUpdateLink?taskId=${task.taskId}" class="edit-button">Edit</a>
                                    <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                                        <input type="hidden" name="taskId" value="${task.taskId}" />
                                        <input type="hidden" name="action" value="toggleStatus" />
                                        <button type="submit" class="task-buttons" style="all: unset; cursor: pointer;">
                                            <img src="images/circle.svg" alt="Toggle Status" />
                                        </button>
                                    </form>
                                </div>
                            </article>
                        </c:if>
                    </c:forEach>
                </div>

                <!-- Assigned Tasks Column -->
                <div class="task-column col-4">
                    <c:forEach var="task" items="${sessionScope.userAssignedTasks}">
                        <c:if test="${!task.taskStatus}">
                            <article class="task-card">
                                <div class="task-elements">
                                    <span class="task-text">${task.taskName}</span>
                                    <span class="task-text">${task.taskDescription}</span>
                                    <a href="deleteTaskLink?taskId=${task.taskId}">Delete</a>
                                    <a href="taskUpdateLink?taskId=${task.taskId}" class="edit-button">Edit</a>
                                    <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                                        <input type="hidden" name="taskId" value="${task.taskId}" />
                                        <input type="hidden" name="action" value="toggleStatus" />
                                        <button type="submit" class="task-buttons" style="all: unset; cursor: pointer;">
                                            <img src="images/circle.svg" alt="Toggle Status" />
                                        </button>
                                    </form>
                                </div>
                            </article>
                        </c:if>
                    </c:forEach>
                </div>

                <!-- Completed Tasks Column -->
                <div class="task-column  col-4">
                    <c:forEach var="task" items="${sessionScope.completedTasks}">
                        <article class="task-card">
                            <div class="task-elements">
                                <span class="task-text">${task.taskName}</span>
                                <span class="task-text">${task.taskDescription}</span>
                                <a href="deleteTaskLink?taskId=${task.taskId}">Delete</a>
                                <a href="taskUpdateLink?taskId=${task.taskId}" class="edit-button">Edit</a>
                                    <c:choose >
                                        <c:when test="${!task.taskStatus}">
                                            <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                                                <input type="hidden" name="taskId" value="${task.taskId}" />
                                                <input type="hidden" name="action" value="toggleStatus" />
                                                <button type="submit" class="task-buttons" style="all: unset; cursor: pointer;">
                                                    <img src="images/circle.svg" alt="Toggle Status" />
                                                </button>
                                            </form>
                                        </c:when>
                                        <c:when test="${task.taskStatus}">
                                            <form action="updateTask" method="post" style="display:inline; margin: 0; padding: 0; border: none; background: none;">
                                                <input type="hidden" name="taskId" value="${task.taskId}" />
                                                <input type="hidden" name="action" value="toggleStatus" />
                                                <button type="submit" class="task-buttons" style="all: unset; cursor: pointer;">
                                                    <img src="images/check_circle.svg" alt="Toggle Status" />
                                                </button>
                                            </form>
                                        </c:when>
                                    </c:choose>
                            </div>
                        </article>
                    </c:forEach>
                </div>
            </div>

            <%@include file="footer.jsp"%>

        </div>
        <script src="JavaScript/app.js"></script>
    </body>
</html>

