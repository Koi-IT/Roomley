<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="head.jsp"%>

    <c:choose>
        <c:when test="${empty userName}">
        <body style="min-height: 1594px">
        <article class="home-page">
        </c:when>
        <c:otherwise>
            <body style="min-height: 955px">
            <article class="page-wrapper">
        </c:otherwise>
    </c:choose>
        <header>
            <nav>
                <ul>
                    <li class="dm-serif-text-regular-italic">Roomley</li>
                </ul>
                <ul>
                    <li class="nav-font"><a href="index.jsp">Home</a></li>
                    <c:choose>
                        <c:when test="${empty userName}">
                            <li class="nav-font"><a href = "logIn">Log in</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-font"><a href="index.jsp">Sign out</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>

        </header>