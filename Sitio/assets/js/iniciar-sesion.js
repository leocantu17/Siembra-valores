const verifCorreo = (correo) => { 
    const arreglo = correo.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)
    if(arreglo) return true
    
    return false
}

document.addEventListener('DOMContentLoaded', () => {
    const btnIniciarSesion = document.getElementById('btn-iniciar-sesion')
    btnIniciarSesion.addEventListener('click', () => {
        const data={
            correo:verifCorreo(document.getElementById('txt-correo').value)?document.getElementById('txt-correo').value:'',
            contraseña:document.getElementById('txt-contraseña').value
        }
        fetch('/rt-iniciar-sesion',{
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