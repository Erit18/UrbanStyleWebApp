document.addEventListener('DOMContentLoaded', function() {
    // Verificar si los elementos existen antes de agregar event listeners
    const container = document.getElementById('container');
    const registerBtn = document.getElementById('register');
    const loginBtn = document.getElementById('login');
    const registroForm = document.getElementById('registroForm');

    if (registerBtn) {
        registerBtn.addEventListener('click', () => {
            if (container) container.classList.add("active");
        });
    }

    if (loginBtn) {
        loginBtn.addEventListener('click', () => {
            if (container) container.classList.remove("active");
        });
    }

    if (registroForm) {
        registroForm.addEventListener('submit', function(e) {
            console.log('Formulario interceptado');
            const formData = new FormData(this);
            for (let pair of formData.entries()) {
                console.log(pair[0] + ': ' + pair[1]);
            }
            return true;
        });
    }

    // Rating stars
    const stars = document.querySelectorAll('.rating input');
    stars.forEach((star) => {
        star.addEventListener('change', function() {
            const rating = this.value;
            alert(`Calificaste la polera con ${rating} estrellas`);
        });
    });

    // Manejar el modal de contraseña olvidada
    const forgotPasswordLink = document.getElementById('forgotPasswordLink');
    const forgotPasswordForm = document.getElementById('forgotPasswordForm');
    let forgotPasswordModal;

    if (forgotPasswordLink) {
        forgotPasswordLink.addEventListener('click', function(e) {
            e.preventDefault();
            forgotPasswordModal = new bootstrap.Modal(document.getElementById('forgotPasswordModal'));
            forgotPasswordModal.show();
        });
    }

    if (forgotPasswordForm) {
        forgotPasswordForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            const email = document.getElementById('recoveryEmail').value;
            const verificationCodeDiv = document.getElementById('verificationCodeDiv');
            const verificationCode = document.getElementById('verificationCode').value;
            const newPasswordDiv = document.getElementById('newPasswordDiv');
            const newPassword = document.getElementById('newPassword')?.value;
            const submitButton = this.querySelector('button[type="submit"]');

            try {
                // Si el campo de nueva contraseña está visible, cambiamos la contraseña
                if (newPasswordDiv.style.display === 'block' && newPassword) {
                    const response = await fetch(`${window.location.origin}${contextPath}/cambiar-password`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `email=${encodeURIComponent(email)}&password=${encodeURIComponent(newPassword)}`
                    });

                    const data = await response.json();
                    if (data.success) {
                        alert('Contraseña cambiada exitosamente');
                        // Cerrar el modal y limpiar el formulario
                        bootstrap.Modal.getInstance(document.getElementById('forgotPasswordModal')).hide();
                        forgotPasswordForm.reset();
                        verificationCodeDiv.style.display = 'none';
                        newPasswordDiv.style.display = 'none';
                        submitButton.textContent = 'Enviar código de verificación';
                    } else {
                        alert(data.message || 'Error al cambiar la contraseña');
                    }
                }
                // Si el campo de código está visible, verificamos el código
                else if (verificationCodeDiv.style.display === 'block' && verificationCode) {
                    const response = await fetch(`${window.location.origin}${contextPath}/verificar-codigo`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `email=${encodeURIComponent(email)}&codigo=${encodeURIComponent(verificationCode)}`
                    });

                    const data = await response.json();
                    if (data.success) {
                        newPasswordDiv.style.display = 'block';
                        submitButton.textContent = 'Cambiar contraseña';
                    } else {
                        alert(data.message || 'Código incorrecto. Por favor, intenta nuevamente.');
                    }
                }
                // Si no hay código ni contraseña nueva, enviamos el correo
                else {
                    const response = await fetch(`${window.location.origin}${contextPath}/recuperar-password`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `email=${encodeURIComponent(email)}`
                    });

                    const data = await response.json();
                    if (data.success) {
                        verificationCodeDiv.style.display = 'block';
                        submitButton.textContent = 'Verificar código';
                        alert('Se ha enviado un código de verificación a tu correo electrónico.');
                    } else {
                        alert(data.message || 'Ha ocurrido un error. Por favor, intenta nuevamente.');
                    }
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Ha ocurrido un error. Por favor, intenta nuevamente.');
            }
        });
    }

    // Agregar esta función al archivo existente
    function validarContraseñas() {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const errorElement = document.getElementById('passwordError');
        
        // Validar que la contraseña tenga al menos 6 caracteres
        if (password.length < 6) {
            errorElement.textContent = 'La contraseña debe tener al menos 6 caracteres';
            errorElement.style.display = 'block';
            return false;
        }
        
        // Validar que las contraseñas coincidan
        if (password !== confirmPassword) {
            errorElement.textContent = 'Las contraseñas no coinciden';
            errorElement.style.display = 'block';
            return false;
        }
        
        // Si todo está bien, ocultar el mensaje de error
        errorElement.style.display = 'none';
        return true;
    }

    // Agregar eventos para validación en tiempo real
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    const errorElement = document.getElementById('passwordError');
    
    function validarEnTiempoReal() {
        if (password.value && confirmPassword.value) {
            if (password.value !== confirmPassword.value) {
                errorElement.textContent = 'Las contraseñas no coinciden';
                errorElement.style.display = 'block';
            } else if (password.value.length < 6) {
                errorElement.textContent = 'La contraseña debe tener al menos 6 caracteres';
                errorElement.style.display = 'block';
            } else {
                errorElement.style.display = 'none';
            }
        }
    }
    
    if (password && confirmPassword) {
        password.addEventListener('input', validarEnTiempoReal);
        confirmPassword.addEventListener('input', validarEnTiempoReal);
    }
});
