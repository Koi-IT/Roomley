<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp" />
<main>

    <table>
        <thead>
        <th>ID</th>
        <th>Task Name</th>
        <th>Status</th>
        <th>Task Description</th>
        </thead>
        <tbody>
        <c:forEach var="task" items="${tasks}">
            <tr>
                <td>${task.id}</td>
                <td>${task.taskName}</td>
                <td>${task.taskStatus}</td>
                <td>${task.taskDescription}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</main>
<c:import url="footer.jsp" />

