const userInfoTbody = document.querySelector('.userInfoTbody');



async function getUserInfoToTable() {
    let output = ``
    await fetch("http://localhost:8080/auth/info")
        .then((response) => {
            return response.json();
        })
        .then((user) => {
            output = "";
            let roles = "";
            user.roles.forEach((role) => {
                roles += role.role + " ";
            })

            output += `
            <tr id=${user.id}>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>${roles}</td>
            </tr>`
            userInfoTbody.innerHTML = ""
            userInfoTbody.innerHTML += output;
        })
}

function alertCustom() {
    alert("YES2")
}
$(document).ready(function() {
    window.addEventListener('load', getUserInfoToTable());
});