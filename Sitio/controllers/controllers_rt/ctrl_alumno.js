const { alumno } = require("../../db/db")

const ctrl_alumnos = {
    eliminar: async (req, res) => {
        try {
            const {id}=req.body
            await alumno.eliminar({ID_US:id})
            return res.json({estatus:"OK",mensaje:"Alumno eliminado"})
        } catch (error) {
            console.log(error)
        }
    },
    agregar: async (req, res) => {
        try {
            const {nombre,apellido,correo,celular,colonia,escuela,fechaNacimiento,contrasena}=req.body
            if(fechaNacimiento==''||nombre==''||apellido==''||correo==''||celular==''||colonia==''||escuela==''||contrasena==''){
                return res.json({estatus:"ERR",mensaje:"Tienes que completar todos los campos"})
            }else{
            await alumno.agregar({nombre,apellido,correo,celular,colonia,escuela,fechaNacimiento,contrasena})
            return res.json({estatus:"OK",mensaje:"Alumno agregado"})
            }
            

        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = ctrl_alumnos