const init = () => {
    const tableElement = document.getElementById("invite-table");
    addInvites(tableElement);

}

const addInvites = table => {
    let row = document.createElement("tr")
    let invitedBy = document.createElement("td")
    let inviteStatus = document.createElement("td")
    let householdName = document.createElement("td")
    let acceptDecline = document.createElement("td")

    invitedBy.textContent();
    inviteStatus.textContent();
    householdName.textContent();
    acceptDecline.textContent();

    table.appendChild(row);
    row.appendChild(invitedBy);
    row.appendChild(inviteStatus);
    row.appendChild(householdName);
    row.appendChild(acceptDecline);
}


if (window.location.pathname.includes("invites")) {
    window.onload = init;
}
