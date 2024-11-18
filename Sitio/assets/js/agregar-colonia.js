document.addEventListener('DOMContentLoaded', () => {
    const btnAgregarColonia = document.getElementById('btn-agregar-colonia')
    btnAgregarColonia.addEventListener('click', () => {
        const data={
            nombre:document.getElementById('nombre-colonia').value,
            ciudad:document.getElementById('ciudad').value,
            minArboles:document.getElementById('min-arboles').value,
            metaArboles:document.getElementById('meta-arboles').value,
            codigoPostal:document.getElementById('codigo-postal').value
        }
        fetch('/rt-agregar-colonia',{
            method:'POST',
            body:JSON.stringify(data),
            headers:{
                'Content-Type':'application/json'
            }
        })
        .then(res=>res.json())
        .then(data=>{
            if(data.estatus=='OK'){
                window.location.href='/Colonias'
            }else{
                alert(data.mensaje)
            }
        }).catch(error=>{
            console.log(error)
        })
    })
})