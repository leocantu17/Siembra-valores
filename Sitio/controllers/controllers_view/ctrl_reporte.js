const { arbol} = require("../../db/db")


const cmsReporte = {
    reporteArbol: async (req, res) => {
        try {
            const {id}=req.session.usuario.id
            const id_arbol=req.query.id
            const resultado=(await arbol.reporte({id,arbol:id_arbol})).datos
            console.log(resultado);
            res.render('reporte-alumnos',{resultado})
        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = cmsReporte