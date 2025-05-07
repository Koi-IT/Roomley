<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="head.jsp" %>

<body class="page-wrapper">
<article class="${empty sessionScope.username ? 'home-page' : 'page-wrapper'}">

    <header>
        <nav>
            <ul>
                <li class="dm-serif-text-regular-italic">Roomley</li>
            </ul>
            <ul>
                <c:choose>
                    <c:when test="${not empty sessionScope.username}">
                        <li class="nav-font"><a href="taskGrabber">Home</a></li>
                        <li class="nav-font"><a href="index.jsp">Profile</a></li>
                        <li class="nav-font"><a href="logout">Sign out</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-font"><a href="index.jsp">Home</a></li>
                        <li class="nav-font"><a href="logIn">Log in</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </header>