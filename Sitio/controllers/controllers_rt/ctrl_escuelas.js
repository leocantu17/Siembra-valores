const { escuela } = require("../../db/db")

const ctrl_escuelas = {
    eliminar: async (req, res) => {
        try {
            const {id}=req.body
            await escuela.eliminar({ID_ESCUELA:id})
            return res.json({estatus:"OK",mensaje:"Escuela eliminada"})
        } catch (error) {
            console.log(error)
        }
    },
    agregar: async (req, res) => {
        try {
            const {nombre,direccion,nivelEducacion,numeroArboles,encargado,colonia}=req.body
            if(nombre==''||direccion==''||nivelEducacion==''||numeroArboles==''||encargado==''||colonia==''){
                return res.json({estatus:"ERR",mensaje:"Tienes que completar todos los campos"})
            }else{
                await escuela.agregar({nombre,direccion,nivelEducacion,numeroArboles,encargado,colonia})
                return res.json({estatus:"OK",mensaje:"Escuela agregada"})
            }
            

        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = ctrl_escuelas