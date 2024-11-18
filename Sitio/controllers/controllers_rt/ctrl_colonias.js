const { colonia } = require("../../db/db")

const ctrl_colonias = {
    eliminar: async (req, res) => {
        try {
            const {id}=req.body
            await colonia.eliminar({ID_COL:id})
            return res.json({estatus:"OK",mensaje:"Colonia eliminada"})
        } catch (error) {
            console.log(error)
        }
    },
    agregar: async (req, res) => {
        try {
            const {nombre,ciudad,minArboles,metaArboles,codigoPostal}=req.body
            if(nombre==''||ciudad==''||minArboles==''||metaArboles==''||codigoPostal==''){
                return res.json({estatus:"ERR",mensaje:"Tienes que completar todos los campos"})
            }else{
                await colonia.agregar({nombre,ciudad,minArboles,metaArboles,codigoPostal})
                return res.json({estatus:"OK",mensaje:"Colonia agregada"})
            }
            

        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = ctrl_colonias