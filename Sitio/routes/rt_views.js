const { Router } = require ( 'express' );
const cmsInicio = require('../controllers/controllers_view/ctrl_inicio');
const router = Router();

router.get ( '/', ( req, res ) => {
    try {
        res.render ( 'tabla_main' );
    } catch (error) {
        console.log ( error );
    }

} );

router.get('/Estadisticas',cmsInicio.estadisticas);
router.get('/Alumnos',cmsInicio.alumnos);
router.get('/Escuelas',cmsInicio.escuelas);
router.get('/Colonias',cmsInicio.colonias);
router.get('/Arboles',cmsInicio.arboles);


module.exports = router