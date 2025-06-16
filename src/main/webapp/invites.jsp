<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 5/14/2025
  Time: 6:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%@include file="header.jsp"%>

    <body class="page-wrapper">
        <main class="welcomeCard">
            <article class="welcomeCard">

                <table id="invite-table">
                    <thead>
                    <tr>
                        <th>Invited By</th>
                        <th>Status</th>
                        <th>Household</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- rows will be inserted here -->
                    </tbody>
                </table>

            </article>

        </main>
    </body>

<%@include file="footer.jsp"%>

</html>
