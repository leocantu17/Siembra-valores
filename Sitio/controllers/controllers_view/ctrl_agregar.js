const { escuela, colonia,alumno } = require("../../db/db")

const cmsAgregar = {
    agregarAlumnos: async (req, res) => {
        try {
            const colonias=(await colonia.visualizar()).datos
            const escuelas=(await escuela.visualizar()).datos
            res.render('agregar-alumno',{colonias,escuelas})
        } catch (error) {
            console.log(error)
        }
    },
    agregarArboles: async (req, res) => {
        try {
            const colonias=(await colonia.visualizar()).datos
            res.render('agregar-arbol',{colonias})
        } catch (error) {
            console.log(error)
        }
    },
    agregarEscuelas: async (req, res) => {
        try {
            const colonias=(await colonia.visualizar()).datos
            res.render('agregar-escuela',{colonias})
        } catch (error) {
            console.log(error)
        }
    },
    agregarColonias: async (req, res) => {
        try {
            res.render('agregar-colonia')
        } catch (error) {
            console.log(error)
        }
    },
    notificacionesAlumno: async (req, res) => {
        try {
            const {id}=req.query
            const resultado=(await alumno.datos({id})).datos[0]
            res.render('Notificaciones',{id,resultado})
        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = cmsAgregar