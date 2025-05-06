<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp"%>

        <main id="home-page">
            <article class="welcomeCard">
                <h1>Welcome to Roomley!</h1>
                <p>Your roommate task solution</p>
            </article>

            <article class="infoCard">
                <h1>Our goal</h1>
                <p>
                    Living with roommates can sometimes be a hassle. Dividing up chores can be tough when it's so easy to forget who does what and when...
                </p>
            </article>

            <article class="infoCard">
                <h1>Features</h1>
                <ul>
                    <li>Ability to fairly assign tasks weekly</li>
                    <li>Notifications that are sent out at set intervals</li>
                    <li>Ability to create lists (grocery lists, etc.) collaboratively</li>
                    <li>Ability to attach context to each task</li>
                </ul>
            </article>

            <article class="welcomeCard">
                <a href="https://us-east-2jnjg4nu3f.auth.us-east-2.amazoncognito.com/signup?response_type=code&client_id=3ct09do7tc83j2vhag3ejelndf&redirect_uri=http://localhost:8080/userdisplayexercise_war/auth">
                    <h1>Sign Up</h1>
                </a>
            </article>
        </main>

<%@include file="footer.jsp"%>
