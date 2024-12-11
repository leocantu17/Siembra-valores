const {appMovilDb, notificaciones} = require('../../db/db');

const ctrl_rAppMovil={
    iniciarSesion:async(req,res)=>{
        const{correo,contrasena}=req.query
        const data={correo,contrasena}
        const resultado=(await appMovilDb.inicirSesionApp(data)).datos
        if(resultado.length>0){
            res.json(resultado)
        }else{
            res.json(resultado)
        }
    },
    adoptarArbol:async(req,res)=>{
        try {
            const {ID_US,ID_ARBOL,ID_VALOR}=req.query
            console.log(ID_US,ID_ARBOL,ID_VALOR)
            const data={ID_US:parseInt(ID_US),ID_ARBOL:parseInt(ID_ARBOL),ID_VALOR:parseInt(ID_VALOR)}
            await appMovilDb.adoptarArbolApp(data)
        } catch (error) {
            console.log(error)
        }
    },
    agregarServicio:async(req,res)=>{
        try {
            const {ID_ARBOL,ID_SERVICIO,COMENTARIOS,ALTURA,CIRCUNFERENCIA,ID_US}=req.query
            const data={ID_US,TIPO,FECHA_SERVICIO,COMENTARIOS,ALTURA,CIRCUNFERENCIA,ID_ARBOL,ID_SERVICIO}
            const resultado=await appMovilDb.agregarServicio(data)
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
    },
    realizarMision:async(req,res)=>{
        try{
            const{ID_US,PUNTOS}=req.query
            const data={ID_US,PUNTOS}
            const resultado=await appMovilDb.realizarMision(data)
        }catch(error){
            console.log(error)
        }
    },
    notificacionesLeida:async(req,res)=>{
        try{
            const {ID_NOT}=req.query
            await notificaciones.notifciacionesLeidoDb({id:ID_NOT})
        }catch(error){
            console.log(error)
        }
    },
    perfilUsuario:async(req,res)=>{
        try{
            const id=req.params.id
            const resultado=await appMovilDb.perfilUsuarioDb({id})
            return res.json(resultado)
        }catch(error){
            console.log(error)
        }
    },
    agregarNotificaciones:async(req,res)=>{
        try{
            await appMovilDb.agregarNotificacionDb()
            return res.json(resultado)
        }catch(error){
            console.log(error)
        }
    }
}

module.exports = ctrl_rAppMovil

