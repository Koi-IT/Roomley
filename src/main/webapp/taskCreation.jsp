<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/4/2025
  Time: 11:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<article>
    <form action="${pageContext.request.contextPath}/taskCreator" method="post">
        <label for="taskName">Task Name:</label>
        <input type="text" id="taskName" name="taskName">
        <label for="taskDescription">Task Description:</label>
        <input type="text" id="taskDescription" name="taskDescription">
        <button type="submit">Create Task</button>
    </form>

</article>



</body>
</html>
