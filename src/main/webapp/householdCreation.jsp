<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/7/2025
  Time: 4:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="title" value="Create Household"/>
<!DOCTYPE html>
<html lang="en">

    <%@include file="header.jsp"%>

    <body class="page-wrapper">
        <article class="${empty sessionScope.username ? 'home-page' : 'page-wrapper'}">

            <article class="welcomeCard">
                <form action="householdCreator" method="post">
                    <label for="householdName">Household Name</label>
                    <input type="text" id="householdName" name="householdName">

                    <label for="addUsers">Add User</label>
                    <input type="text" id="addUsers" name="addUsers">
                    <button type="button" id="addUsersBtn">Add User</button>
                    <br><br>
                    <h3>Users Added</h3>
                    <br><br>
                    <ul id="userList"></ul>

                    <div id="usersContainer" style="display:none;"></div>

                    <button type="submit">Create Household</button>
                </form>
            </article>

            <%@include file="footer.jsp"%>

        </article>
    </body>
</html>

