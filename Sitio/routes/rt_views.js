const { Router } = require ( 'express' );
const cmsInicio = require('../controllers/controllers_view/ctrl_inicio');
const cmsSesiones = require('../controllers/controllers_view/ctrl_sesiones');
const cmsReporte = require('../controllers/controllers_view/ctrl_reporte');
const cmsAgregar = require('../controllers/controllers_view/ctrl_agregar');
const ctrl_rAppMovil = require('../controllers/controllers_rt/ctrl_rtAppMovil');
const appMovil = require('../controllers/controllers_view/ctrl_appMovil');
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

/*Rutas de la app movil */
router.get("/rt-arboles-disponibles",appMovil.arbolesDisponibles)
router.get("/rt-informacion-arbol",appMovil.informacionArbol)
router.get("/rt-historial-serivicios",appMovil.historialServicios)
router.get("/rt-notificaciones-alumno",appMovil.notificacionesAlumno)
router.get("/rt-obtener-valores",appMovil.obtenerValores)
router.get("/rt-obtener-info-arbol",appMovil.misArbolesInfo)
router.get("/rt-mis-arboles",appMovil.misArboles)
router.get("/rt-obtener-servicios",appMovil.obtenerServicios)
router.get("/rt-perfil-usuario",appMovil.perfilUsuario)

module.exports = router