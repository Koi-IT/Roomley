<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp"%>
<main>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Task Name</th>
                <th>Status</th>
                <th>Task Description</th>
            </tr>
        </thead>
        <tbody>
        <c:if test="${empty tasks}">
            <tr>
                <td colspan="4">No tasks available</td>
            </tr>
        </c:if>
        <c:forEach var="task" items="${tasks}">
            <tr>
                <td>${task.taskId}</td>
                <td>${task.taskName}</td>
                <td>${task.taskStatus}</td>
                <td>${task.taskDescription}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

</main>
<c:import url="footer.jsp" />

