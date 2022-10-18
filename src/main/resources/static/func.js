const url = 'http://localhost:8080/api/'

//главная таблица пользователей
export function fillUsersTable() {
    const allUsersTableBody = document.getElementById('allUsersTableBody')


    $('#allUsersTableBody').empty()
    fetch(url)
        .then(response => response.json())
        .then(data => {
            let columnContent = ''
            data.forEach(element => {
                columnContent += `<tr>
                    <td>${element.id}</td>
                    <td>${element.name}</td>
                    <td>${element.lastname}</td>
                    <td>${element.username}</td>
                    <td>${element.roles.map(role => role.name.substring(5))}</td>
                    <td>
                    <button type="button" class="btn btn-danger delete" id="buttonDelete"
                    data-index="${element.id}" data-bs-target="#modalDelete" data-bs-toggle="modal">
                    Удалить
                    </button>
                    </td>
                    <td>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" id="buttonEdit"
                    data-index="${element.id}" data-bs-target="#modalEdit">
                    Изменить
                    </button>
                    </td>
                    <td>
                    </td>
                </tr>
                `
            })
            allUsersTableBody.innerHTML = columnContent

        })
}

//заполнение таблицы текущего пользователя
export function fullCurrentUserTable() {
    const currentUserTableBody = document.getElementById('currentUserTableBody')
    const currentUserLogin = document.getElementById('currentUserLogin')
    const currentUserRoles = document.getElementById('currentUserRoles')
    fetch(url + 'principal')
        .then(response => response.json())
        .then(data => {
            let userRoles = data.roles.map(role => role.name.substring(5))
            let columnContent = ''
            columnContent += `<tr>
                    <td>${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.lastname}</td>
                    <td>${data.username}</td>
                    <td>${userRoles}</td>
                </tr>
                `
            currentUserTableBody.innerHTML = columnContent
            currentUserLogin.innerText = data.username
            currentUserRoles.innerText = userRoles
        })
}

//выбор ролей для нового пользователя
export function getRolesForNewUser() {
    const selectRolesForNewUser = document.getElementById('selectRolesForNewUser')
    fetch('http://localhost:8080/api/roles')
        .then(response => response.json())
        .then(data => {
            let resRoles = ''
            data.forEach(element => {
                if (element.id === 2) {
                    resRoles +=
                        `
                    <option value='${element.id}' selected>
                    ${element.name.substring(5)}
                    </option>
                    `
                } else {
                    resRoles +=
                        `
                    <option value='${element.id}' >
                    ${element.name.substring(5)}
                    </option>
                    `
                }
            })
            selectRolesForNewUser.innerHTML = resRoles
        })
}

//создание нового пользователя
export function createNewUser(e) {
    e.preventDefault()
    const newUserForm = document.forms['createUserForm']
    let newUserRoles = []
    for (let option of document.getElementById('selectRolesForNewUser').options) {
        if (option.selected) {
            newUserRoles.push({
                id: option.value,
                name: 'ROLE_' + option.innerText
            })
        }
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: newUserForm.name.value,
            lastname: newUserForm.lastname.value,
            username: newUserForm.username.value,
            password: newUserForm.password.value,
            roles: newUserRoles
        })
    }).then((response) => {
            if (response.ok) {
                newUserForm.reset()
                fillUsersTable()
                getSuccessMessage('Пользователь создан успешно!')
                $('.nav-tabs a[href="#UserTable"]').tab('show')
            } else {
                response.json()
                    .then((res) => {
                        getErrorMessage(res, newUserForm)
                    })
            }
        }
    )
}

//заполнение форм (редактирования, удаления)
export function fillUserForm(id, formName, method) {
    fetch(url + id)
        .then(response => response.json())
        .then(data => {
            formName.id.value = data.id
            formName.name.value = data.name
            formName.lastname.value = data.lastname
            formName.username.value = data.username
            let rolesForEditedUser = document.getElementById('roles' + method)
            let userRolesId = []
            data.roles.forEach(role => {
                userRolesId.push(role.id)
            })
            fetch('http://localhost:8080/api/roles')
                .then(response => response.json())
                .then(data => {
                    let resRoles = ''
                    data.forEach(element => {
                        if (userRolesId.includes(element.id)) {
                            resRoles +=
                                `
                    <option value='${element.id}' selected>
                    ${element.name.substring(5)}
                    </option>
                    `
                        } else {
                            resRoles +=
                                `
                    <option value='${element.id}' >
                    ${element.name.substring(5)}
                    </option>
                    `
                        }
                    })
                    rolesForEditedUser.innerHTML = resRoles
                })
        })
}

//редактирование пользователя
export function updateCurrentUser(e) {
    e.preventDefault()
    let editUserRoles = []
    for (let option of document.getElementById('rolesEdit').options) {
        if (option.selected) {
            editUserRoles.push({
                id: option.value,
                name: 'ROLE_' + option.innerText
            })
        }
    }
    let userEditForm = document.forms['editUserModalForm']
    fetch(url + 'patch', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: userEditForm.id.value,
            name: userEditForm.name.value,
            lastname: userEditForm.lastname.value,
            username: userEditForm.username.value,
            password: userEditForm.password.value,
            roles: editUserRoles
        })
    }).then((response) => {
            if (response.ok) {
                fillUsersTable()
                userEditForm.password.value = ''
                document.getElementById('closeEditModalWindow').click()
                getSuccessMessage('Пользователь успешно изменен!')
                $('.nav-tabs a[href="#UserTable"]').tab('show')
            } else {
                response.json()
                    .then((res) => {
                        getErrorMessage(res, userEditForm)
                    })
            }
        }
    )
}

//удаление пользователя
export function deleteCurrentUser(id) {
    fetch(url + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(() => {
        fillUsersTable()
        document.getElementById('closeDeleteModal').click()
        getSuccessMessage('Пользователь успешно удален!')
        $('.nav-tabs a[href="#UserTable"]').tab('show')
    })
}

//получение окна сообщения с ошибками
function getErrorMessage(errorJSON, form) {
    let errorBody = document.getElementById('errorBody')
    let errorBodyText = ''
    for (let line of errorJSON.message.split(';')) {
        errorBodyText +=
            `
             <a>${line}</a>
             <br>
             `
    }
    console.log(errorJSON.message)
    errorBody.innerHTML = errorBodyText
    form.password.value = ''
    $('#errorModal').modal('toggle')
}

//получение окна выполнения операции
function getSuccessMessage(message) {
    $('#successModal').modal('toggle')
    document.getElementById('successBody').innerHTML =
        `
        <a>${message}</a>
        `
}