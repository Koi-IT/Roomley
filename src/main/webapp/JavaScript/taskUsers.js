const init = () => {
    const userList = document.getElementById("userList");
    const addUserBtn = document.getElementById("addUsersBtn");
    addUserBtn.addEventListener("click", addUserToList);

}






if (window.location.pathname.includes("householdCreation")) {
    window.onload = init;
}
