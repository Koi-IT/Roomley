const init2 = () => {
    const tableBody = document.querySelector("#invite-table tbody");

    if (!memberId) {
        console.error("Member ID not found in session");
        return;
    }

    console.log("MemberId:", memberId);

    // Logging the full URL to ensure the memberId is part of the request
    const url = `https://fwhy2rgxqj.execute-api.us-east-2.amazonaws.com/dev?memberId=${encodeURIComponent(memberId)}`;
    console.log("Requesting URL:", url);

    fetch(`https://fwhy2rgxqj.execute-api.us-east-2.amazonaws.com/dev?memberId=${encodeURIComponent(memberId)}`)
        .then(response => response.json())
        .then(invites => {
            console.log("Invites:", invites);
            if (Array.isArray(invites)) {
                addInvites(tableBody, invites);
            } else {
                console.error("Expected an array of invites, got:", invites);
            }
        })
        .catch(error => {
            console.error("Failed to load invites", error);
        });

};

const addInvites = (table, invites) => {
    console.log("addInvites called with invites:", invites);
    invites.forEach(invite => {
        let row = document.createElement("tr");

        let invitedBy = document.createElement("td");
        invitedBy.textContent = invite.invited_by_user;

        let inviteStatus = document.createElement("td");
        inviteStatus.textContent = invite.status;

        let householdName = document.createElement("td");
        householdName.textContent = invite.household_id;

        let acceptDecline = document.createElement("td");
        acceptDecline.textContent = "Accept | Decline";

        row.appendChild(invitedBy);
        row.appendChild(inviteStatus);
        row.appendChild(householdName);
        row.appendChild(acceptDecline);

        table.appendChild(row);
    });
};

if (window.location.pathname.includes("invite")) {
    window.onload = init2;
}
