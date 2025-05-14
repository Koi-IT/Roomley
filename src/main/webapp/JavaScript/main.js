
const init = () => {
    const userList = document.getElementById("userList");
    const addUserBtn = document.getElementById("addUsersBtn");
    addUserBtn.addEventListener(onclick, addUserToList)

}

const addUserToList = () => {
    const input = document.getElementById("addUsers");
    const username = input.value.trim();

    if (username !== "") {
        // Add to visible list
        const listItem = document.createElement("li");
        listItem.textContent = username;
        document.getElementById("userList").appendChild(listItem);

        // Add hidden input to send with form
        const hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "users";
        hiddenInput.value = username;
        document.getElementById("hiddenUserInputs").appendChild(hiddenInput);

        input.value = "";
    }
}

if (window.location.pathname.includes("householdCreator")) {
    window.onload = init;
}
