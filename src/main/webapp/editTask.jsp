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
            <img src="${pageContext.request.contextPath}/images/circle.svg" alt="circle">
        </a>
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




