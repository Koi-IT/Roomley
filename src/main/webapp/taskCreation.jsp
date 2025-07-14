<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/4/2025
  Time: 11:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="title" value="Create Task"/>
<!DOCTYPE html>
<html lang="en">

    <%@include file="header.jsp"%>

    <body class="page-wrapper">
        <article class="${empty sessionScope.user.username ? 'home-page' : 'page-wrapper'}">

        <article class="welcomeCard">
            <form action="${pageContext.request.contextPath}/taskCreator" method="post">
                <label for="taskName">Task Name:</label>
                <input type="text" id="taskName" name="taskName" required>

                <label for="taskDescription">Task Description:</label>
                <input type="text" id="taskDescription" name="taskDescription" required>

                <label for="taskDifficulty">Task Difficulty:</label>
                <select name="taskDifficulty" id="taskDifficulty">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select>

                <button type="submit">Create Task</button>
            </form>

        </article>

        <%@include file="footer.jsp"%>

        </article>
    </body>
</html>
