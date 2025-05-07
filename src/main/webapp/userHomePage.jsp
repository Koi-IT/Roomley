<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/5/2025
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>

<div class="grid">
    <article class="userProfile welcomeCard">
        <button>
            Distribute Tasks
        </button>
    </article>
    <article class="welcomeCard">
        <h3 class="dm-serif-text-regular-italic">Welcome</h3>
        <h3 class="dm-serif-text-regular-italic">${sessionScope.username}</h3>
    </article>
    <article class="createHousehold welcomeCard">
        <button>
            Create Household
        </button>
    </article>
</div>

<!-- Task Buttons Section -->
<div class="grid">
    <article class="task-title">
        <h3>Tasks to-do</h3>
        <a href="taskCreation.jsp">
            <img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="add task">
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
                <c:if test="${sessionScope.userSub != null && sessionScope.userHousehold != null}">
                    <article class="task-card">
                        <div class="task-elements">
                            <span class="task-text">${task.taskName}</span>
                            <span class="task-text">${task.taskDescription}</span>
                            <a href="editTask?taskId=${task.taskId}" class="edit-button">Edit</a>
                            <a href="updateTask?taskId=${task.taskId}" class="task-buttons">
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
                        <a href="editTask?taskId=${task.taskId}" class="edit-button">Edit</a>
                        <a href="updateTask?taskId=${task.taskId}" class="task-buttons">
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
    </div>

    <!-- Completed Tasks Column -->
    <div class="task-column  col-4">
        <c:forEach var="task" items="${sessionScope.completedTasks}">
            <article class="task-card">
                <div class="task-elements">
                    <span class="task-text">${task.taskName}</span>
                    <span class="task-text">${task.taskDescription}</span>
                    <a href="editTask?taskId=${task.taskId}" class="edit-button">Edit</a>
                    <a href="updateTask?taskId=${task.taskId}" class="task-buttons">
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
                    </a>
                </div>
            </article>
        </c:forEach>
    </div>
</div>



<%@include file="footer.jsp"%>
