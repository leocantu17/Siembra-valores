const { Router } = require ( 'express' );
const cmsInicio = require('../controllers/controllers_view/ctrl_inicio');
const cmsSesiones = require('../controllers/controllers_view/ctrl_sesiones');
const cmsReporte = require('../controllers/controllers_view/ctrl_reporte');
const cmsAgregar = require('../controllers/controllers_view/ctrl_agregar');
const { app_movil } = require('../db/db');
const router = Router();

router.get('/',cmsSesiones.login)
router.get('/cerrar-sesion',cmsSesiones.logout)
router.get('/Estadisticas',cmsInicio.estadisticas);
router.get('/Alumnos',cmsInicio.alumnos);
router.get('/Escuelas',cmsInicio.escuelas);
router.get('/Colonias',cmsInicio.colonias);
router.get('/Arboles',cmsInicio.arboles);
router.get('/agregar-alumnos',cmsAgregar.agregarAlumnos);
router.get('/agregar-arboles',cmsAgregar.agregarArboles);
router.get('/agregar-escuelas',cmsAgregar.agregarEscuelas);
router.get('/agregar-colonias',cmsAgregar.agregarColonias);
router.get('/notificaciones-alumno',cmsAgregar.notificacionesAlumno);
router.get('/reporte-arbol',cmsReporte.reporteArbol);
router.get('/session', (req,res)=>{
    try{
        res.json(req.session)
    }catch(error){
        console.log(error)
    }
})
router.get("/rt-prueba",async(req,res)=>{
    const resultado=await app_movil.pruebaApp()
    
    res.json(resultado.datos)
})
module.exports = router