document.addEventListener('DOMContentLoaded', () => {
    const btnAgregarAlumno = document.getElementById('btn-agregar-alumno')
    btnAgregarAlumno.addEventListener('click', () => {
        const data={
            nombre:document.getElementById('nombre').value,
            apellido:document.getElementById('apellido-paterno').value,
            apellidoMaterno:document.getElementById('apellido-materno').value,
            correo:document.getElementById('correo').value,
            contrasena:document.getElementById('contrasena').value,
            celular:document.getElementById('celular').value,
            colonia:document.getElementById('colonia').value,
            escuela:document.getElementById('escuela').value,
            fechaNacimiento:document.getElementById('fecha-nacimiento').value
        }
        fetch('/rt-agregar-alumno',{
            method:'POST',
            body:JSON.stringify(data),
            headers:{
                'Content-Type':'application/json'
            }
        })
        .then(res=>res.json())
        .then(data=>{
            if(data.estatus=='OK'){
                window.location.href='/Alumnos'
            }else{
                alert(data.mensaje)
            }
        }).catch(error=>{
            console.log(error)
        })
    })
})