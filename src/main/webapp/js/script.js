const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');
const registroForm = document.getElementById('registroForm');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});

// Agregar manejo del formulario de registro
if (registroForm) {
    registroForm.addEventListener('submit', function(e) {
        console.log('Formulario interceptado');
        
        // Debug de los datos del formulario
        const formData = new FormData(this);
        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }
        
        // Permitir que el formulario continúe
        return true;
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const stars = document.querySelectorAll('.rating input');
    
    stars.forEach((star) => {
        star.addEventListener('change', function() {
            const rating = this.value;
            alert(`Calificaste la polera con ${rating} estrellas`);
        });
    });
});
