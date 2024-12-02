 // Cargar el header
 fetch("../catalogo/fragments/header.html")
 .then(response => response.text())
 .then(data => {
     document.getElementById("header-placeholder").innerHTML = data;
     
     // Inicializar la búsqueda después de que el header esté cargado
     const searchInput = document.getElementById('searchInput');
     
     // Evento para el formulario de búsqueda
     document.querySelector('form.d-flex').addEventListener('submit', function(event) {
         event.preventDefault();
         realizarBusqueda();
     });

     // Evento para búsqueda en tiempo real
     searchInput.addEventListener('input', realizarBusqueda);
 });

// Cargar el footer
fetch("../catalogo/fragments/footer.html")
 .then(response => response.text())
 .then(data => document.getElementById("footer-placeholder").innerHTML = data);

// Función de búsqueda
function realizarBusqueda() {
 const searchTerm = document.getElementById('searchInput').value.toLowerCase();
 const cards = document.querySelectorAll('.card');
 let hasResults = false;

 cards.forEach(card => {
     const title = card.querySelector('.card-title').textContent.toLowerCase();
     const price = card.querySelector('.card-text').textContent.toLowerCase();
     const cardContainer = card.closest('.col-md-3');

     if (title.includes(searchTerm) || price.includes(searchTerm)) {
         cardContainer.style.display = '';
         hasResults = true;
     } else {
         cardContainer.style.display = 'none';
     }
 });

 // Mostrar u ocultar mensaje de "no hay resultados"
 const noResultsMessage = document.getElementById('no-results-message');
 noResultsMessage.style.display = hasResults || searchTerm === '' ? 'none' : 'block';
}