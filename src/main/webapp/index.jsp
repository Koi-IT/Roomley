<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp"%>

<c:choose>
    <c:when test="${empty userName}">
        <main id="homePage">
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
    </c:when>
    <c:otherwise>
        <article class="welcomeCard">
            <h3 class="dm-serif-text-regular-italic">Welcome</h3>
            <h3 class="dm-serif-text-regular-italic">${userName}</h3>
        </article>

        <!-- Task Buttons Section -->
        <div class="grid">
            <button type="button">Tasks to-do
                <a href="taskCreation.jsp">
                    <img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="add task">
                </a>
            </button>
            <button type="button">Assigned to me
                <a href="taskCreation.jsp">
                    <img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="add task">
                </a>
            </button>
            <button type="button">Finished tasks
                <a href="taskCreation.jsp">
                    <img src="images/add_circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="add task">
                </a>
            </button>
        </div>

        <!-- Display tasks -->
        <div class="grid">
            <c:forEach var="task" items="${tasks}">
                <article class="task-card flex w-full">
                    <div class="task-elements flex justify-between items-center w-full">
                        <span class="task-text">${task.taskName}</span>
                        <a href="#" class="edit-button">Edit</a>
                        <a href="#" class="task-buttons">
                            <img src="images/circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="circle">
                        </a>
                    </div>
                </article>
            </c:forEach>
        </div>

        <!-- Display user-assigned tasks -->
        <div class="grid">
            <c:forEach var="task" items="${userAssignedTasks}">
                <article class="task-card flex w-full">
                    <div class="task-elements flex justify-between items-center w-full">
                        <span class="task-text">${task.taskName}</span>
                        <a href="#" class="edit-button">Edit</a>
                        <a href="#" class="task-buttons">
                            <img src="images/circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="circle">
                        </a>
                    </div>
                </article>
            </c:forEach>
        </div>

        <!-- Display completed tasks -->
        <div class="grid">
            <c:forEach var="task" items="${completedTasks}">
                <article class="task-card flex w-full">
                    <div class="task-elements flex justify-between items-center w-full">
                        <span class="task-text">${task.taskName}</span>
                        <a href="#" class="edit-button">Edit</a>
                        <a href="#" class="task-buttons">
                            <img src="images/circle_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg" alt="circle">
                        </a>
                    </div>
                </article>
            </c:forEach>
        </div>

    </c:otherwise>
</c:choose>

<%@include file="footer.jsp"%>
