const { Router } = require ( 'express' );
const sesiones = require('../controllers/controllers_rt/ctrl_sesiones');
const ctrl_alumnos = require('../controllers/controllers_rt/ctrl_alumno');
const ctrl_escuelas = require('../controllers/controllers_rt/ctrl_escuelas');
const ctrl_colonias = require('../controllers/controllers_rt/ctrl_colonias');
const ctrl_arboles = require('../controllers/controllers_rt/ctrl_arboles');
const router = Router();

router.post("/rt-iniciar-sesion",sesiones.iniciarSesion)
router.post("/rt-eliminar-alumno",ctrl_alumnos.eliminar)
router.post("/rt-agregar-alumno",ctrl_alumnos.agregar)
router.post("/rt-eliminar-escuela",ctrl_escuelas.eliminar)
router.post("/rt-agregar-escuela",ctrl_escuelas.agregar)
router.post("/rt-eliminar-colonia",ctrl_colonias.eliminar)
router.post("/rt-agregar-colonia",ctrl_colonias.agregar)
router.post("/rt-eliminar-arbol",ctrl_arboles.eliminar)
router.post("/rt-agregar-arbol",ctrl_arboles.agregar)

module.exports = router