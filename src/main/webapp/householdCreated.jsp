<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/13/2025
  Time: 9:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<!DOCTYPE html>
<html lang="en">

<%@include file="header.jsp"%>

<body class="page-wrapper">

<article class="welcomeCard">
  <h1>Your Household: ${sessionScope.currentHousehold.groupName}</h1>
  <h1>has been created</h1>
</article>

<article class="welcomeCard">
  <h2>Household Members Successfully Added</h2>
  <c:forEach var="user" items="${sessionScope.foundUsers}">
    <div class="task-elements">
      <p>${user}</p>
    </div>
  </c:forEach>
</article>

<article class="welcomeCard">
  <h2>Household Members Not Found</h2>
  <c:forEach var="user" items="${sessionScope.missingUsers}">
    <div class="task-elements">
      <p>${user}</p>
    </div>
  </c:forEach>
</article>



<%@include file="footer.jsp"%>

</body>
</html>
