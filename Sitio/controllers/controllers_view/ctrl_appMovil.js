const {appMovilDb} = require('../../db/db');

const appMovil={
    arbolesDisponibles:async(req,res)=>{
        try {
            const id=req.query.id
            const resultado=(await appMovilDb.arbolesDb({id})).datos    
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
    },
    informacionArbol:async(req,res)=>{
        try {
            const id=req.query.id
            const resultado=(await appMovilDb.informacionArbolDb({id})).datos
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
        
    },
    misArbolesInfo:async(req,res)=>{
        try{
            const id=req.query.ID_ARBOL
            const resultado=(await appMovilDb.misArbolesDbInfo({ID_ARBOL:id})).datos
            return res.json(resultado)
        }catch(error){
            console.log(error)
        }
    },
    misArboles:async(req,res)=>{
        try {
            const id=req.query.ID_US
            const resultado=(await appMovilDb.misArbolesDb({ID_US:id})).datos
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
    },
    
    historialServicios:async(req,res)=>{
        try {
            const id=req.query.ID_US
            const resultado=(await appMovilDb.historialServiciosDb({id})).datos
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
    },
    perfilUsuario:async(req,res)=>{
        try {
            const {ID_US}=req.query
            const resultado=(await appMovilDb.perfilUsuarioDb({ID_US})).datos
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
    },
    notificacionesAlumno:async(req,res)=>{
        try {
            const {ID_US}=req.params.query
            const resultado=await appMovilDb.notificacionesAlumnoDb({id:ID_US})
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
    },
    obtenerValores:async(req,res)=>{
        try {
            const resultado=(await appMovilDb.valoresDb()).datos
            return res.json(resultado)
        } catch (error) {
            console.log(error)
        }
},
    obtenerServicios:async(req,res)=>{
        try{
            const resultado=(await appMovilDb.serviciosDb()).datos
            return res.json(resultado)
        }catch(error){
            console.log(error)
        }
    }
}

module.exports = appMovil