document.addEventListener('DOMContentLoaded', () => {
   const btnAgregarNotificacion = document.getElementById('btn-agregar-notificacion')
   btnAgregarNotificacion.addEventListener('click', () => {
       const data={
           id:document.getElementById('id').value,
           mensaje:document.getElementById('mensaje').value
       }
       fetch('/rt-agregar-notificacion',{
           method:'POST',
           body:JSON.stringify(data),
       }).then(res=>res.json())
       .then(data=>{
           if(data.estatus=='OK'){
               window.location.reload()
           }else{
               alert(data.mensaje)
           }
       }).catch(error=>{
           console.log(error)
       })
    })
})       