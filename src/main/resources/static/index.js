getAllUsers()

function getAllUsers() {
    fetch('http://localhost:8080/admin/list').then(res => {
        res.json().then(tableAllUsers => {
            if (tableAllUsers.length > 0) {
                var temp = ""
                tableAllUsers.forEach(user => {
                    temp += '<tr>'
                    temp += '<td>' + user.id + '</td>'
                    temp += '<td>' + user.firstName + '</td>'
                    temp += '<td>' + user.surname + '</td>'
                    temp += '<td>' + user.age + '</td>'
                    temp += '<td>' + user.email + '</td>'
                    temp += '<td>' + user.roles.map(r => r.name) + '</td>'
                    temp += '<td>' + '<button type=\'button\' data-toggle=\'modal\' class=\'btn-info btn btn-editUser\'' +
                        ' data-target=\'#editUserModal\' data-user-id=\'' + user.id +
                        '\'>Edit</button>' + '</td>'
                    temp += '<td>' + '<button type=\'button\' data-toggle=\'modal\' class=\'btn btn-danger btn-deleteUser\'' +
                        ' data-target=\'#deleteUserModal\' data-user-id=\'' + user.id +
                        '\'>Delete</button>' + '</td>'
                })
                document.getElementById('tableAllUsers').innerHTML = temp
            }
        })
    })
}


//Edit
$('#editUserModal').on('show.bs.modal', (e) => {
    let userId = $(e.relatedTarget).data('user-id');
    fetch('http://localhost:8080/admin/' + userId).then(res => {
        res.json().then(userData => {
            console.log(userData)
            document.getElementById('editId').value = userData.id;
            document.getElementById('editFirstname').value = userData.firstName;
            document.getElementById('editSurname').value = userData.surname;
            document.getElementById('editAge').value = userData.age;
            document.getElementById('editEmail').value = userData.email;
            document.getElementById('editPassword').value = userData.password;
            fetch('http://localhost:8080/admin/roles').then(rol => {
                rol.json().then(roleData => {
                    roleData.map(role => {
                        $('#editRoles').append(
                            $('<option>').text(role.name)
                        )
                    })
                })
            })
        })
    })
})
//Нажатие edit в окне
$('#editSub').on('click', (e) => {
    e.preventDefault()

    let editUser = {
        id: parseInt($('#editId').val()),
        firstName: $('#editFirstname').val(),
        surname: $('#editSurname').val(),
        age: $('#editAge').val(),
        email: $('#editEmail').val(),
        password: $('#editPassword').val(),
        roleName: $('#editRoles').val()
    }
    console.log(editUser)
    fetch('/admin', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(editUser)
    }).then(res => {
        if (res.status == 200) {
            location.reload()
        }
    })
})

// //Delete
$('#deleteUserModal').on('show.bs.modal', (e) => {
    let userId = $(e.relatedTarget).data('user-id');
    fetch('http://localhost:8080/admin/' + userId).then(res => {
        res.json().then(delUserData => {
            console.log(delUserData)
            document.getElementById('delId').value = delUserData.id;
            document.getElementById('delFirstName').value = delUserData.firstName;
            document.getElementById('delSurname').value = delUserData.surname;
            document.getElementById('delAge').value = delUserData.age;
            document.getElementById('delEmail').value = delUserData.email;
            document.getElementById('delPassword').value = delUserData.password;
        })
    })
})
//Нажатие delete в окне
$('#delSub').on('click', (e) => {
    e.preventDefault()

    let delUser = {
        id: parseInt($('#delId').val()),
        firstName: $('#delFirstname').val(),
        surname: $('#delSurname').val(),
        age: $('#delAge').val(),
        email: $('#delEmail').val(),
        password: $('#delPassword').val()
    }
    fetch('/admin/' + delUser.id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(delUser)
    }).then(res => {
        if (res.status == 200) {
            location.reload()
        }
    })
})

//Создание нового user
$('[href="#newUserTab"]').on('show.bs.tab', (e) => {
    fetch('http://localhost:8080/admin/roles').then(res => {
        res.json().then(newUser => {
            document.getElementById('newFirstName');
            document.getElementById('newSurname');
            document.getElementById('newAge');
            document.getElementById('newEmail');
            document.getElementById('newPassword');
            $.each(newUser, function (k, role) {
                $('#newRoles').append(
                    $('<option>').text(role.name)
                )
            })
        })
    })
})
$('#newButton').on('click', (e) => {
    e.preventDefault()

    let newUser = {
        id: parseInt($('#newId').val()),
        firstName: $('#newFirstName').val(),
        surname: $('#newSurname').val(),
        age: $('#newAge').val(),
        email: $('#newEmail').val(),
        password: $('#newPassword').val(),
        roleName: $('#newRoles').val()
    }
    fetch('http://localhost:8080/admin', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    })
    getAllUsers()
    $('#upTab a[href="#v-pills-admin-tab"]').tab('show')
    location.reload()

})


//Информация об пользователе
$('[href="#v-pills-user"]').on('show.bs.tab', (e) => {
    getUserInfo()
})

function getUserInfo() {
    fetch('http://localhost:8080/admin/getUser').then(rs => {
        rs.json().then(user => {
            var temp = ""
            temp += '<tr>'
            temp += '<td>' + user.id + '</td>'
            temp += '<td>' + user.firstName + '</td>'
            temp += '<td>' + user.surname + '</td>'
            temp += '<td>' + user.age + '</td>'
            temp += '<td>' + user.email + '</td>'
            temp += '<td>' + user.roles.map(r => r.name) + '</td>'
            document.getElementById('infUser').innerHTML = temp
        })
    })
}

