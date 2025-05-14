const init = () => {
    const userList = document.getElementById("userList");
    const addUserBtn = document.getElementById("addUsersBtn");
    addUserBtn.addEventListener("click", addUserToList);

}

const addUserToList = () => {
    const input = document.getElementById("addUsers");
    const username = input.value.trim();

    if (username !== "") {
        // Add the username to the visible list
        const listItem = document.createElement("article");
        listItem.textContent = username;
        document.getElementById("userList").appendChild(listItem);

        // Create a hidden input for the user to be submitted with the form
        const userInputElement = document.createElement("input");
        userInputElement.type = "hidden"; // Use hidden input for form submission
        userInputElement.name = "users[]"; // This will send users as an array
        userInputElement.value = username;

        // Append to a hidden container in the form (make sure this container exists in the HTML)
        document.getElementById("usersContainer").appendChild(userInputElement);

        // Log the form data (for debugging)
        const form = document.querySelector("form");
        const formData = new FormData(form);
        for (let pair of formData.entries()) {
            console.log(pair[0] + ": " + pair[1]);
        }

        // Clear the input field after adding
        input.value = "";
    }
}

if (window.location.pathname.includes("householdCreation")) {
    window.onload = init;
}
