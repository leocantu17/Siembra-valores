document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('form-arbol');
    const btnAgregarCuidado = document.getElementById('btn-agregar-cuidado');
    const mensajeError = document.getElementById('mensaje-error');
    let contadorCuidados = 1;

    // Asegurarse de que el primer cuidado esté abierto por defecto
    const primerosCuidados = document.querySelectorAll('.cuidado-item');
    if (primerosCuidados.length > 0) {
        primerosCuidados[0].classList.add('open');
    }

    // Agregar cuidado
    btnAgregarCuidado.addEventListener('click', () => {
        if (contadorCuidados >= 5) {
            mensajeError.style.display = 'block';
            return;
        }

        const nuevoCuidado = document.createElement('div');
        nuevoCuidado.classList.add('cuidado-item');

        nuevoCuidado.innerHTML = `
            <button type="button" class="toggle-cuidado">Cuidados ${contadorCuidados + 1}</button>
            <div class="form-row cuidado-content">
                <label for="nombre-cuidado-${contadorCuidados}">Nombre:</label>
                <input type="text" id="nombre-cuidado-${contadorCuidados}" name="nombre-cuidado">
            </div>
            <div class="form-row cuidado-content">
                <label for="descripcion-${contadorCuidados}">Descripción:</label>
                <input type="text" id="descripcion-${contadorCuidados}" name="descripcion">
            </div>
            <div class="form-row cuidado-content">
                <label for="frecuencia-${contadorCuidados}">Frecuencia:</label>
                <input type="text" id="frecuencia-${contadorCuidados}" name="frecuencia">
            </div>
        `;
        
        // Insertar el nuevo cuidado antes del botón de agregar
        form.insertBefore(nuevoCuidado, btnAgregarCuidado);

        // Evento para manejar el clic en el botón de cada cuidado
        const toggleCuidadoButton = nuevoCuidado.querySelector('.toggle-cuidado');
        const cuidadoContent = nuevoCuidado.querySelector('.cuidado-content');

        toggleCuidadoButton.addEventListener('click', () => {
            nuevoCuidado.classList.toggle('open'); // Alterna la clase para expandir o minimizar
        });

        contadorCuidados++;
        mensajeError.style.display = 'none';
    });

    // Enviar datos al backend
    const btnAgregarArbol = document.getElementById('btn-agregar-arbol');
    btnAgregarArbol.addEventListener('click', async () => {
        const datosFormulario = extraerDatos(form);
        const response = await fetch('/rt-agregar-arbol', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(datosFormulario),
        });
        
        if (response.ok) {
            const data = await response.json();
        
            if (data.estatus === "OK") {
                window.location.href = '/Arboles';
            } else {    
                alert(data.mensaje);
            }
        }
        
    });
});

// Extraer datos del formulario
function extraerDatos(form) {
    const formData = new FormData(form);
    const datos = {};

    formData.forEach((value, key) => {
        // Si hay varios campos con el mismo nombre, agrúpalos en un array
        if (key in datos) {
            if (!Array.isArray(datos[key])) {
                datos[key] = [datos[key]];
            }
            datos[key].push(value);
        } else {
            datos[key] = value;
        }
    });

    return datos;
}
