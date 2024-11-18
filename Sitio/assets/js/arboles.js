function eliminar(id){
    const data={
        id:id
    }
    fetch('/rt-eliminar-arbol',{
        method:'POST',
        body:JSON.stringify(data),
        headers:{
            'Content-Type':'application/json'
        }
    })
    .then(res=>res.json())
    .then(data=>{
        if(data.estatus=='OK'){
            window.location.href='/Arboles'
        }else{
            alert(data.mensaje)
        }
    }).catch(error=>{
        console.log(error)
    })
}
