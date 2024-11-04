const { prueba } = require("../../db/db");
const { paginacion } = require("../../extras/paginacion");

const cmsInicio = {
    estadisticas: async (req, res) => {
        try {
            res.render('estadisticas')
        } catch (error) {
            console.log(error)
        }
    },
    alumnos: async (req, res) => {
    try {
        const por_p=parseInt(req.query.d)||10;
        const pagina=parseInt(req.query.p)||1;
        const offset=(pagina-1)*por_p;
        const estatus=parseInt(req.query.e)||2;
        const fecha=parseInt(req.query.f)||0
        const b=req.query.b||'';
        //Datos para la busqueda en la BD;
        const campos_busqueda=['U.NOMBRE','U.AP_P','U.AP_M','U.ID_US','E.NOMBRE','U.CORREO']
        const like=b.split(' ');
        const busqueda=like.map(term=> `CONCAT(${campos_busqueda}) LIKE '%${term}%'`).join('OR')
    const data={
        tabla: `USUARIO U JOIN ALUMNO A ON U.ID_US=A.ID_US JOIN ESCUELA E ON A.ID_ESCUELA=E.ID_ESCUELA`,
                campos:['U.ID_US','U.CORREO',`CONCAT(U.NOMBRE,' ',U.AP_P,' ',U.AP_M) AS NOMBRE_COMP`,`FORMAT(U.FECHA_REGISTRO,'dd/MM/yy hh:mm:ss ') AS FECHA_REGISTRO`,'E.NOMBRE','U.ACTIVO'], pagina:offset, 
                por_p, campos_busqueda:busqueda, cam_est:'U.ACTIVO', estatus, cam_fecha: 'U.FECHA_REGISTRO', fecha,orden:`U.ID_US`
    }
        const resultado=(await prueba.prueba(data)).datos;
        //Paginacion
        const total=resultado.map(elemento=>elemento.SIZE);
        const paginas=paginacion({act:pagina,total:total[0]||0, por_p});
        const tabla='alumnos'
  
        if(req.headers.peticion=='fetch'){
            res.render('partials/alumnos', {tabla,prueba:resultado, muestra: por_p,
                paginas, lugar:'alumnos'})
        }else{
            res.render('tabla_main',{tabla,prueba:resultado,paginas, muestra: por_p, 
               lugar: 'alumnos'})
              }
    } catch (error) {
        console.log(error)
    }
    },
    escuelas: async (req, res) => {
    try {
        const por_p=parseInt(req.query.d)||10;
        const pagina=parseInt(req.query.p)||1;
        const offset=(pagina-1)*por_p;
        const estatus=parseInt(req.query.e)||2;
        const fecha=parseInt(req.query.f)||0
        const b=req.query.b||'';
        //Datos para la busqueda en la BD;
        const campos_busqueda=['E.NOMBRE','E.ID_ESCUELA','E.NIVEL_EDUCATIVO','C.NOMBRE']
        const like=b.split(' ');
        const busqueda=like.map(term=> `CONCAT(${campos_busqueda}) LIKE '%${term}%'`).join('OR')
    const data={
        tabla: `ESCUELA E JOIN COLONIA C ON E.ID_COL=C.ID_COL`,
                campos:['E.ID_ESCUELA','E.NOMBRE','E.NIVEL_EDUCATIVO',`FORMAT(E.FECHA_REGISTRO,'dd/MM/yy hh:mm:ss ') AS FECHA_REGISTRO`,'E.ACTIVO','C.NOMBRE AS NOMBRE_COL'], pagina:offset, 
                por_p, campos_busqueda:busqueda, cam_est:'E.ACTIVO', estatus, cam_fecha: 'E.FECHA_REGISTRO', fecha,orden:`E.ID_ESCUELA`
    }
        const resultado=(await prueba.prueba(data)).datos;
        //Paginacion
        const total=resultado.map(elemento=>elemento.SIZE);
        const paginas=paginacion({act:pagina,total:total[0]||0, por_p});
        const tabla='escuelas'
  
        if(req.headers.peticion=='fetch'){
            res.render('partials/escuelas', {tabla,prueba:resultado, muestra: por_p,
                paginas, lugar:'escuelas'})
        }else{  
            res.render('tabla_main',{tabla,prueba:resultado,paginas, muestra: por_p, 
               lugar: 'escuelas'})
              }
    } catch (error) {
        console.log(error)
    }
    },
    colonias: async (req, res) => {
    try {
        const por_p=parseInt(req.query.d)||10;
        const pagina=parseInt(req.query.p)||1;
        const offset=(pagina-1)*por_p;
        const estatus=parseInt(req.query.e)||2;
        const fecha=parseInt(req.query.f)||0
        const b=req.query.b||'';
        //Datos para la busqueda en la BD;
        const campos_busqueda=['NOMBRE','CIUDAD','COD_POSTAL']
        const like=b.split(' ');
        const busqueda=like.map(term=> `CONCAT(${campos_busqueda}) LIKE '%${term}%'`).join('OR')
    const data={
        tabla: `COLONIA`, 
                campos:['ID_COL','NOMBRE','CIUDAD','COD_POSTAL',`FORMAT(FECHA_REGISTRO,'dd/MM/yy hh:mm:ss ') AS FECHA_REGISTRO`,'ACTIVO','META','MINIMO_ARBOLES'], pagina:offset, 
                por_p, campos_busqueda:busqueda, cam_est:'ACTIVO', estatus, cam_fecha: 'FECHA_REGISTRO', fecha,orden:`ID_COL`
    }
        const resultado=(await prueba.prueba(data)).datos;
        //Paginacion
        const total=resultado.map(elemento=>elemento.SIZE);
        const paginas=paginacion({act:pagina,total:total[0]||0, por_p});
        const tabla='colonias'
  
        if(req.headers.peticion=='fetch'){
            res.render('partials/colonias', {tabla,prueba:resultado, muestra: por_p,
                paginas, lugar:'colonias'})
        }else{  
            res.render('tabla_main',{tabla,prueba:resultado,paginas, muestra: por_p, 
               lugar: 'colonias'})
              }
    } catch (error) {
        console.log(error)
    }
    },
    arboles: async (req, res) => {
    try {
        const por_p=parseInt(req.query.d)||10;
        const pagina=parseInt(req.query.p)||1;
        const offset=(pagina-1)*por_p;
        const estatus=parseInt(req.query.e)||2;
        const fecha=parseInt(req.query.f)||0
        const b=req.query.b||'';
        //Datos para la busqueda en la BD;
        const campos_busqueda=['A.NOMBRE_CIENTIFICO','A.FECHA_PLANTADO','C.NOMBRE','V.VALOR']
        const like=b.split(' ');
        const busqueda=like.map(term=> `CONCAT(${campos_busqueda}) LIKE '%${term}%'`).join('OR')
    const data={
        tabla: `ARBOL A JOIN COLONIA C ON A.ID_COL=C.ID_COL LEFT JOIN VALORES V ON A.ID_VALOR=V.ID_VALOR`,
        campos:['A.ID_ARBOL','A.NOMBRE_CIENTIFICO','V.VALOR',`FORMAT(A.FECHA_REGISTRO,'dd/MM/yy hh:mm:ss ') AS FECHA_REGISTRO`,'A.VIVO','C.NOMBRE','V.VALOR'], pagina:offset, 
                por_p, campos_busqueda:busqueda, cam_est:'A.VIVO', estatus, cam_fecha: 'A.FECHA_REGISTRO', fecha,orden:`A.ID_ARBOL`
    }
        const resultado=(await prueba.prueba(data)).datos;
        //Paginacion
        const total=resultado.map(elemento=>elemento.SIZE);
        const paginas=paginacion({act:pagina,total:total[0]||0, por_p});
        const tabla='arboles'
  
        if(req.headers.peticion=='fetch'){
            res.render('partials/arboles', {tabla,prueba:resultado, muestra: por_p,
                paginas, lugar:'arboles'})
        }else{  
            res.render('tabla_main',{tabla,prueba:resultado,paginas, muestra: por_p, 
               lugar: 'arboles'})
              }
    } catch (error) {
        console.log(error)
    }
    },
}

module.exports = cmsInicio