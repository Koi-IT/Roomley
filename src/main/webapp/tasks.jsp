<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>
<main>

    <table>
        <thead>
        <th>ID</th>
        <th>Task Name</th>
        <th>Status</th>
        <th>Task Description</th>
        </thead>
        <tbody>
        <c:forEach var="tasks" items="${task}">
            <tr>
                <td>${tasks.id}</td>
                <td>${tasks.task_name}</td>
                <td>${tasks.complete}</td>
                <td>${tasks.task_description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</main>
<%@include file="footer.jsp"%>

