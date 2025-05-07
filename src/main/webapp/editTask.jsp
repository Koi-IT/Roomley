<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/6/2025
  Time: 3:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<article class="task-card">
    <div class="task-elements">
        <span class="task-text">${sessionScope.taskToUpdate.taskName}</span>
        <span class="task-text">${sessionScope.taskToUpdate.taskDescription}</span>
        <a href="#" class="task-buttons">
            <img src="${pageContext.request.contextPath}/images/circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="circle">
        </a>
    </div>
</article>


<form action="" method="post" >
    <label for="taskName">Task Name</label>
    <input type="text" name="taskName" id="taskName">
    <label for="taskDescription">Task Description</label>
    <input type="text" name="taskDescription" id="taskDescription">
    <label for="taskStatus">Task Status</label>
    <input type="text" name="taskStatus" id="taskStatus">
    <button>Update</button>
    <button>Delete</button>
</form>




