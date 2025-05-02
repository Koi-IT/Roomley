<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp"%>
    <main>
        <h1><a href="taskGrabber">Click here to get the DB list of tasks</a></h1>
    </main>
    <body>
    <c:choose>
        <c:when test="${empty userName}">
            <a href = "logIn">Log in</a>
        </c:when>
        <c:otherwise>
            <h3>Welcome ${userName}</h3>
        </c:otherwise>
    </c:choose>
    </body>
<%@include file="footer.jsp"%>


