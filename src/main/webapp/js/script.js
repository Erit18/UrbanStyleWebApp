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
});
