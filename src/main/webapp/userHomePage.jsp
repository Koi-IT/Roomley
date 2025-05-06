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

<article class="welcomeCard">
    <h3 class="dm-serif-text-regular-italic">Welcome</h3>
    <h3 class="dm-serif-text-regular-italic">${sessionScope.username}</h3>
</article>

<!-- Task Buttons Section -->
<div class="grid">
    <button type="button">Tasks to-do
        <a href="taskCreation.jsp">
            <img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="add task">
        </a>
    </button>
    <button type="button">Assigned to me
        <a href="taskCreation.jsp">
            <img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="add task">
        </a>
    </button>
    <button type="button">Finished tasks
        <a href="taskCreation.jsp">
            <img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="add task">
        </a>
    </button>
</div>

<!-- Task Columns Section -->
<div class="grid">
    <!-- To-Do Tasks Column -->
    <div class="col-4">
        <c:forEach var="task" items="${sessionScope.tasks}">
            <article class="task-card">
                <div class="task-elements">
                    <span class="task-text">${task.taskName}</span>
                    <a href="#" class="edit-button">Edit</a>
                    <a href="#" class="task-buttons">
                        <img src="images/circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="circle">
                    </a>
                </div>
            </article>
        </c:forEach>
    </div>

    <!-- Assigned Tasks Column -->
    <div class="col-4">
        <c:forEach var="task" items="${sessionScope.assignedTasks}">
            <article class="task-card">
                <div class="task-elements">
                    <span class="task-text">${task.taskName}</span>
                    <a href="#" class="edit-button">Edit</a>
                    <a href="#" class="task-buttons">
                        <img src="images/circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="circle">
                    </a>
                </div>
            </article>
        </c:forEach>
    </div>

    <!-- Completed Tasks Column -->
    <div class="col-4">
        <c:forEach var="task" items="${sessionScope.completedTasks}">
            <article class="task-card">
                <div class="task-elements">
                    <span class="task-text">${task.taskName}</span>
                    <a href="#" class="edit-button">Edit</a>
                    <a href="#" class="task-buttons">
                        <img src="images/circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="circle">
                    </a>
                </div>
            </article>
        </c:forEach>
    </div>
</div>



<%@include file="footer.jsp"%>
