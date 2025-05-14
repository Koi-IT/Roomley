<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
    <header class="container">
        <nav>

            <ul>
                <li class="dm-serif-text-regular-italic">Roomley</li>
            </ul>

            <ul>
                <c:choose>
                    <c:when test="${empty sessionScope.username}">
                        <!-- When the user is not logged in -->
                        <li class="nav-font"><a href="index.jsp">Home</a></li>
                        <li class="nav-font"><a href="logIn">Log in</a></li>
                    </c:when>
                    <c:otherwise >
                        <!-- When the user is logged in -->
                        <li class="nav-font"><a href="taskGrabber">Home</a></li>
                        <li class="nav-font"><a href="userProfile.jsp">Profile</a></li>
                        <li class="nav-font"><a href="invites.jsp">Invites</a></li>
                        <li class="nav-font"><a href="logout">Sign out</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>

        </nav>
    </header>