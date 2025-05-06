<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="head.jsp" %>

<body style="${empty sessionScope.username ? 'min-height: 1594px' : 'min-height: 955px'}">
<article class="${empty sessionScope.username ? 'home-page' : 'page-wrapper'}">

    <header>
        <nav>
            <ul>
                <li class="dm-serif-text-regular-italic">Roomley</li>
            </ul>
            <ul>
                <li class="nav-font"><a href="index.jsp">Home</a></li>
                <c:choose>
                    <c:when test="${not empty sessionScope.username}">
                        <li class="nav-font"><a href="logout">Sign out</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-font"><a href="logIn">Log in</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </header>