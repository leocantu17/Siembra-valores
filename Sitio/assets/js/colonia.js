function eliminar(id){
    const data={
        id:id
    }
    fetch('/rt-eliminar-colonia',{
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
}