const{notificaciones}=require("../../db/db")

const ctrl_notificaciones = {
    agregar: async (req, res) => {
        try {
            const {id}=req.body
            await notificaciones.agregar({id_us:id,mensaje:req.body.mensaje})
            return res.json({estatus:"OK",mensaje:"Notificación enviada"})
        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = ctrl_notificaciones