const allUsersTableBody = document.querySelector('.allUsersTableBody');

class Role {
    constructor(id) {
        this.id = id;
    }
}
let output = ``
$(document).ready(function() {
    window.addEventListener('load', getUsersToTable());
});

async function getUsersToTable() {
    await fetch("http://localhost:8080/api/users")
        .then((response) => {
            return response.json();
        }).then((users) => {
            output = "";
            users.forEach((user) => {
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
                       <td data-id="${user.id}">
                          <button
                             id="edit-button"
                             class="btn btn-info edit-button"
                             style="color: white"
                             data-toggle="modal"
                             data-target="#editModal"
                             onclick='showEditModal(${user.id})'
                             >
                          Edit
                          </button>
                          <div
                             id="editModal"
                             data-id="${user.id}"
                             class="modal fade"
                             tabindex="-1"
                             aria-labelledby="editModalLabel"
                             aria-hidden="true"
                             >
                             <div class="modal-dialog">
                                <div class="modal-content">
                                   <div class="modal-header">
                                      <h5 class="modal-title" id="editModalLabel">Edit user</h5>
                                      <button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close"></button>
                                   </div>
                                   <div class="modal-body editModal">
                                   </div>
                                   <div class="modal-footer">
                                      <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                      Close
                                      </button>
                                      <button type="submit"
                                         id="editModalEditUsrBtn"
                                         class="btn btn-primary">
                                      Edit
                                      </button>
                                   </div>
                                </div>
                             </div>
                          </div>
                       </td>
                       <td data-id="${user.id}">
                          <button
                             onclick='showDeleteModal(${user.id})'
                             id="delete-button"
                             class="btn btn-danger button-custom-delete action-button delete-button"
                             data-toggle="modal"
                             data-target="#deleteModal">
                          Delete
                          </button>
                          <div id="deleteModal" class="modal fade" data-id="${user.id}" tabindex="-1">
                             <div class="modal-dialog">
                                <div class="modal-content">
                                   <div class="modal-header">
                                      <h5 class="modal-title" id="deleteModalLabel">Delete user</h5>
                                      <button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close"></button>
                                   </div>
                                   <div class="modal-body deleteModal">
                                   </div>
                                   <div class="modal-footer">
                                      <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                      Close</button>
                                      <button
                                         id="deleteModalDeleteUsrBtn"
                                         type="button"
                                         class="btn btn-danger">
                                      Delete
                                      </button>
                                   </div>
                                </div>
                             </div>
                          </div>
                       </td>
                    </tr>`
            })
            allUsersTableBody.innerHTML = ""
            allUsersTableBody.innerHTML += output;
        })
}

function showDeleteModal(id) {
    let body = "";
    let url = "http://localhost:8080/api/users/" + id;
    fetch(url)
        .then((resp) => resp.json())
        .then((data) => {
            console.log("Got this user for deleting " + data);
            body += `
            <form className="text-center" id="formDeleteUser">
               <div class="row bg-white border">
                  <div class="col">
                  </div>
                  <div class="col">
                     <div class="mb-3">
                        <label for="id" class="form-label d-flex justify-content-center"><b>Id</b></label>
                        <input type="text"
                           id="id"
                           name="id"
                           readonly="readonly"
                           value="${data.id}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="edit-username" class="form-label d-flex justify-content-center"><b>Username</b></label>
                        <input type="text"
                           id="edit-username"
                           name="username"
                           disabled="disabled"
                           value="${data.username}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="edit-name" class="form-label d-flex justify-content-center"><b>Name</b></label>
                        <input type="text"
                           id="edit-name"
                           name="name"
                           disabled="disabled"
                           value="${data.name}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="edit-age" class="form-label d-flex justify-content-center"><b>Age</b></label>
                        <input type="number"   id="edit-age"
                           aria-describedby="emailHelp"
                           name="age"
                           disabled="disabled"
                           value="${data.age}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="edit-password" class="form-label d-flex justify-content-center"><b>Password</b></label>
                        <input class="form-control"
                           type="password"
                           disabled="disabled"
                           value="${data.password}"
                           name="password"
                           id="password"
                           readonly>
                     </div>
                     <div class="mb-3">
                        <label for="allRoles" class="form-label d-flex justify-content-center"><b>Role</b></label>
                        <select multiple class="form-control"
                           id="allRoles"
                           disabled="disabled"
                           name="allRoles">
                           <option value="1">Admin</option>
                           <option value="2">User</option>
                        </select>
                     </div>
                  </div>
                  <div class="col">
                  </div>
               </div>
            </form>`
            document.querySelector('.deleteModal').innerHTML = body;
        })
        .catch((err) => {
            console.log(err);
        })
    document.querySelector('#deleteModalDeleteUsrBtn').addEventListener('click', deleteUser);
}

function showEditModal(id) {
    let body = "";
    let url = "http://localhost:8080/api/users/" + id;
    fetch(url)
        .then((resp) => resp.json())
        .then((data) => {
            console.log("Got this user for editing " + data);
            body += `
            <form data-id="editForm" id="formEditUser" class="formEditUser">
               <div class="row bg-white border">
                  <div class="col">
                  </div>
                  <div class="col">
                     <div class="mb-3">
                        <label for="id" class="form-label d-flex justify-content-center"><b>Id</b></label>
                        <input type="text"
                           id="id"
                           name="id"
                           readonly="readonly"
                           value="${data.id}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="username" class="form-label d-flex justify-content-center"><b>Username</b></label>
                        <input type="text"
                           id="username"
                           name="username"
                           value="${data.username}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="name" class="form-label d-flex justify-content-center"><b>Name</b></label>
                        <input type="text"
                           id="name"
                           name="name"
                           value="${data.name}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="age" class="form-label d-flex justify-content-center"><b>Age</b></label>
                        <input type="number"   id="age"
                           aria-describedby="emailHelp"
                           name="age"
                           value="${data.age}"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="password" class="form-label d-flex justify-content-center"><b>Password</b></label>
                        <input class="form-control"
                           type="password"
                           name="password"
                           id="password"
                           >
                     </div>
                     <div class="mb-3">
                        <label for="roles" class="form-label d-flex justify-content-center"><b>Role</b></label>
                        <select multiple class="form-control"
                           id="allRoles"
                           name="roles">
                           <option value="1">Admin</option>
                           <option value="2">User</option>
                        </select>
                     </div>
                  </div>
                  <div class="col">
                  </div>
               </div>
            </form>`
            document.querySelector('.editModal').innerHTML = body;
        })
        .catch((err) => {
            console.log(err);
        })
    document.querySelector('#editModalEditUsrBtn').addEventListener('click', updateUser);
}

async function deleteUser() {
    const formData = await new FormData(await document.querySelector("#formDeleteUser"));
    let id = formData.get('id');
    console.log(id);
    try {
        const url = "http://localhost:8080/api/users/" + id;
        const response = await fetch(url, {
            method: 'DELETE'
        }).then(resp => {
            console.log(resp)
            $('#deleteModal').modal('hide')
        }).then(getUsersToTable);

    } catch (e) {
        alert(e.name)
        console.error(e);
    }
}

async function addUser() {
    const url = "http://localhost:8080/api/users";
    let form = null;
    const formData = await new FormData(form = await document.querySelector("#newUserForm"));
    const existingRoles = Array.from(formData.getAll('roles'))
    let roles = (existingRoles).map(role => new Role(role));
    let data = {
        username: formData.get('username'),
        name: formData.get('name'),
        age: formData.get('age'),
        password: formData.get('password'),
        roles: roles
    }
    console.log(data)
    let response;
    try {
        response = fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        }).then(resp => {
            if (!resp?.ok) {
                console.log('not ok')
                return resp.json().then((user) => {
                    console.log(user)
                    alert(user.info)
                })
            }
        }).then(data => {
            form.reset();
        }).then(getUsersToTable)
    } catch (err) {
        alert(err.name);
        console.log('yes I got it here')
        console.log(err)
    }

    $('.new-user-table-tab').removeClass('active show');
    $('.user-table-tab').addClass('active show')
    $('.new-user-tab-pane').removeClass('active show');
    $('.user-tab-pane').addClass('active show')
}


document.querySelector("#addNewUserBtn").addEventListener('click', addUser);

async function updateUser() {
    const formData = await new FormData(await document.querySelector("#formEditUser"));
    const existingRoles = Array.from(formData.getAll('roles'))
    let roles = (existingRoles).map(role => new Role(role));
    let data = {
        id: formData.get('id'),
        username: formData.get('username'),
        name: formData.get('name'),
        age: formData.get('age'),
        password: formData.get('password'),
        roles: roles
    }
    console.log(data);

    try {
        const response = await fetch("http://localhost:8080/api/users/", {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        }).then(resp => {
            if (!resp?.ok) {
                console.log('not ok')
                return resp.json().then((user) => {
                    console.log(user)
                    alert(user.info)
                })
            }
        }).then(data => {
            console.log("Edit user with id: " + formData.get('id'))
            $('#editModal').modal('hide');
            getUsersToTable();
        })
    } catch (err) {
        console.log(err);
    }
}