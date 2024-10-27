// Al inicio del archivo, agregar una constante para la URL base
const BASE_URL = window.location.pathname.substring(0, window.location.pathname.indexOf('/views'));

document.addEventListener('DOMContentLoaded', function() {
    cargarUsuarios();

    document.getElementById('btnNuevoUsuario').addEventListener('click', function() {
        document.getElementById('usuarioForm').reset();
        document.getElementById('usuarioId').value = '';
        document.getElementById('usuarioModalLabel').textContent = 'Nuevo Usuario';
        document.getElementById('contraseña').required = true;
        new bootstrap.Modal(document.getElementById('usuarioModal')).show();
    });

    document.getElementById('guardarUsuario').addEventListener('click', guardarUsuario);
});

function cargarUsuarios() {
    fetch(BASE_URL + '/usuario')
        .then(response => response.json())
        .then(usuarios => {
            const tbody = document.getElementById('tablaUsuarios');
            tbody.innerHTML = '';

            usuarios.forEach(usuario => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${usuario.id}</td>
                    <td>${usuario.nombre}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.rol}</td>
                    <td>${new Date(usuario.fechaRegistro).toLocaleDateString()}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editarUsuario(${usuario.id})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="eliminarUsuario(${usuario.id})">Eliminar</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error('Error al cargar usuarios:', error));
}

function guardarUsuario() {
    const usuario = {
        id: document.getElementById('usuarioId').value || null,
        nombre: document.getElementById('nombre').value,
        email: document.getElementById('email').value,
        contraseña: document.getElementById('contraseña').value,
        rol: document.getElementById('rol').value
    };

    // Validar que la contraseña no esté vacía al crear nuevo usuario
    if (!usuario.id && !usuario.contraseña) {
        alert('La contraseña es requerida para nuevos usuarios');
        return;
    }

    console.log('Enviando usuario:', {...usuario, contraseña: '****'});

    const method = usuario.id ? 'PUT' : 'POST';
    const url = usuario.id ? 
        `${BASE_URL}/usuario/${usuario.id}` : 
        `${BASE_URL}/usuario`;

    // Si estamos editando y no se proporcionó contraseña, la eliminamos del objeto
    if (method === 'PUT' && !usuario.contraseña) {
        delete usuario.contraseña;
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(usuario)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || 'Error en la respuesta del servidor');
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Usuario guardado:', data);
        bootstrap.Modal.getInstance(document.getElementById('usuarioModal')).hide();
        cargarUsuarios();
    })
    .catch(error => {
        console.error('Error al guardar usuario:', error);
        alert('Error al guardar usuario: ' + error.message);
    });
}

function editarUsuario(id) {
    fetch(`${BASE_URL}/usuario/${id}`)
        .then(response => response.json())
        .then(usuario => {
            document.getElementById('usuarioId').value = usuario.id;
            document.getElementById('nombre').value = usuario.nombre;
            document.getElementById('email').value = usuario.email;
            document.getElementById('rol').value = usuario.rol;
            document.getElementById('contraseña').value = '';
            document.getElementById('contraseña').required = false;

            document.getElementById('usuarioModalLabel').textContent = 'Editar Usuario';
            new bootstrap.Modal(document.getElementById('usuarioModal')).show();
        })
        .catch(error => console.error('Error al cargar usuario para editar:', error));
}

function eliminarUsuario(id) {
    if (confirm('¿Está seguro de que desea eliminar este usuario?')) {
        fetch(`${BASE_URL}/usuario/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            console.log('Usuario eliminado:', data);
            cargarUsuarios();
        })
        .catch(error => console.error('Error al eliminar usuario:', error));
    }
}
