const { usuario } = require("../../db/db")
const init = require('connect-session-sequelize');

const sesiones = {
    iniciarSesion: async (req, res) => {
        try {
            const{correo,contraseña}=req.body
            console.log(req.query)
            console.log("llegue")
            if(correo=='' || contraseña==''){
                return res.json({"estatus":"ERR","mensaje":"Llena todos los campos"})
            }else{
                const resultado=await usuario.iniciarSesion({correo,contraseña})
                if(resultado.datos.length>0){
                    init;
                req.session.usuario = {
                  id: (resultado.datos.map((elemento) => elemento.ID_US))[0]
                }
                return res.json({estatus:"OK",mensaje:"Sesión iniciada"})
                }else{
                    return res.json({estatus:"ERR",mensaje:"Usuario o contraseña incorrectos"})
                }
                
            }
        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = sesiones