document.addEventListener('DOMContentLoaded', () => {
    const btnAgregarEscuela = document.getElementById('btn-agregar-escuela')
    btnAgregarEscuela.addEventListener('click', () => {
        const data={
            nombre:document.getElementById('nombre-escuela').value,
            direccion:document.getElementById('direccion').value,
            nivelEducacion:document.getElementById('nivel-educacion').value,
            numeroArboles:document.getElementById('numero-arboles').value,
            encargado:document.getElementById('encargado').value,
            colonia:document.getElementById('colonia').value
        }
        fetch('/rt-agregar-escuela',{
            method:'POST',
            body:JSON.stringify(data),
            headers:{
                'Content-Type':'application/json'
            }
        })
        .then(res=>res.json())
        .then(data=>{
            if(data.estatus=='OK'){
                window.location.href='/Escuelas'
            }else{
                alert(data.mensaje)
            }
        }).catch(error=>{
            console.log(error)
        })
    })
})