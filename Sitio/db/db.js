const { sql, sqlConn } = require("../config/conexion");

const objetoResultadoSQL = (resultado) => {
  if (resultado.recordsets.length > 1) {
    return { estatus: "OK", datos: resultado.recordsets };
  }

  if (!resultado.recordset) {
    return { estatus: "OK", datos: [] };
  }

  return { estatus: "OK", datos: resultado.recordset };
};


const prueba={
  prueba:async(datos)=>{
      try {
        const resultado=await sqlConn.request()
        .input('B',sql.NVarChar,datos.b_like)
        .input('SIZE',sql.Int,datos.pagina)
        .input('ESTATUS',sql.Int,datos.estatus)
        .input('FECHA',sql.Int,datos.fecha)
        .query(`
        SELECT
        ${datos.campos},COUNT(1) OVER() AS SIZE FROM ${datos.tabla} WHERE 
        ${datos.campos_busqueda} 
        AND ${datos.cam_est}=IIF(@ESTATUS=2,1 ,0) 
        AND ${datos.cam_fecha} >= 
        CASE WHEN @FECHA=1 THEN DATEADD(DAY,-7,[dbo].OBTENER_FECHA()) 
        WHEN @FECHA=2 THEN DATEADD(MONTH,-1,[dbo].OBTENER_FECHA()) 
        WHEN @FECHA=3 THEN DATEFROMPARTS(YEAR([dbo].OBTENER_FECHA()),1,1) 
        ELSE ${datos.cam_fecha} END 
        ORDER BY ${datos.orden} ASC  
        OFFSET @SIZE ROWS FETCH NEXT ${datos.por_p} ROWS ONLY`)

        return objetoResultadoSQL(resultado)
      } catch (error) {
        throw error
      }
  }  
};

const usuario={
  iniciarSesion:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .input('CORREO',sql.NVarChar,datos.correo)
      .input('CONTRASENA',sql.NVarChar,datos.contraseña)
      .query(`SELECT ID_US FROM USUARIO WHERE CORREO=@CORREO AND CONTRASENA COLLATE Latin1_General_CS_AS=@CONTRASENA`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
}
}

const alumno={
   eliminar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('ID_US',sql.Int,datos.ID_US)
       .query(`UPDATE USUARIO SET ACTIVO=0 WHERE ID_US=@ID_US`)
     } catch (error) {
       throw error
     }
   },
   agregar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('NOMBRE',sql.NVarChar,datos.nombre)
       .input('AP_P',sql.NVarChar,datos.apellido)
       .input('AP_M',sql.NVarChar,datos.apellidoMaterno)
       .input('CORREO',sql.NVarChar,datos.correo)
       .input('CELULAR',sql.NVarChar,datos.celular)
       .input('ID_COL',sql.Int,datos.colonia)
       .input('ID_ESCUELA',sql.Int,datos.escuela)
       .input('FECHA_NACIMIENTO',sql.Date,datos.fechaNacimiento)
       .input('CONTRASENA',sql.NVarChar,datos.contrasena)
       .query(`EXEC ALTA_ALUMNO @CORREO,@CONTRASENA,@CELULAR,@FECHA_NACIMIENTO,@NOMBRE,@AP_P,@AP_M,@ID_ESCUELA,@ID_COL`)
       return true
     } catch (error) {
       throw error
     }
   },
   datos:async(datos)=>{
     try {
       const resultado=await sqlConn.request()
       .input('ID_US',sql.Int,datos.id)
       .query(`SELECT ID_US,NOMBRE FROM USUARIO WHERE ID_US=@ID_US`)
       return objetoResultadoSQL(resultado)
     } catch (error) {
       throw error
     }
   },
 }

 const arbol={
   eliminar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('ID_ARBOL',sql.Int,datos.ID_ARBOL)
       .query(`UPDATE ARBOL SET VIVO=0 WHERE ID_ARBOL=@ID_ARBOL`)
     } catch (error) {
       throw error
     }
   },
   reporte:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .input('ID_US',sql.Int,datos.id)
      .input('ID_ARBOL',sql.Int,datos.arbol)
      .query(`EXEC GENERARREPORTE @ID_ARBOL,@ID_US`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
   },
   id_arbol:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .query(`SELECT ID_ARBOL FROM ARBOL ORDER BY ID_ARBOL DESC`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
 },
 agregar:async(datos)=>{
   try {
     await sqlConn.request()
     .input('NOMBRE_CIENTIFICO',sql.NVarChar,datos.nombreCientifico)
     .input('FECHA_PLANTADO',sql.Date,datos.fechaPlantado)
     .input('ENDEMICO',sql.NVarChar,datos.endemico)
     .input('COLONIA',sql.Int,datos.colonia)
     .query(`EXEC ALTA_ARBOLES @NOMBRE_CIENTIFICO,@FECHA_PLANTADO,@ENDEMICO,@COLONIA`)
     return true
   } catch (error) {
     throw error
   }
 },
}

 const colonia={
   eliminar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('ID_COL',sql.Int,datos.ID_COL)
       .query(`UPDATE COLONIA SET ACTIVO=0 WHERE ID_COL=@ID_COL`)
     } catch (error) {
       throw error
     }
   },
   visualizar:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .query(`SELECT ID_COL,NOMBRE FROM COLONIA`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
   },
   agregar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('NOMBRE',sql.NVarChar,datos.nombre)
       .input('CIUDAD',sql.NVarChar,datos.ciudad)
       .input('MINIMO_ARBOLES',sql.Int,datos.minArboles)
       .input('META',sql.Int,datos.metaArboles)
       .input('COD_POSTAL',sql.NVarChar,datos.codigoPostal)
       .query(`INSERT INTO COLONIA(NOMBRE,CIUDAD,COD_POSTAL,META,MINIMO_ARBOLES,FECHA_REGISTRO) VALUES(@NOMBRE,@CIUDAD,@COD_POSTAL,@META,@MINIMO_ARBOLES,(SELECT DBO.OBTENER_FECHA()))`)
       return true
     } catch (error) {
       throw error
     }
   },
 }

 const app_movil={
  pruebaApp:async()=>{
    try {
      const resultado=await sqlConn.request()
      .query(`SELECT ID_US AS id,NOMBRE as nombre FROM USUARIO`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
  }
 }

 const escuela={
   eliminar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('ID_ESCUELA',sql.Int,datos.ID_ESCUELA)
       .query(`UPDATE ESCUELA SET ACTIVO=0 WHERE ID_ESCUELA=@ID_ESCUELA`)
     } catch (error) {
       throw error
     }
   },
   visualizar:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .query(`SELECT ID_ESCUELA,NOMBRE FROM ESCUELA`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
   },
   agregar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('NOMBRE',sql.NVarChar,datos.nombre)
       .input('DIRECCION',sql.NVarChar,datos.direccion)
       .input('NIVEL_EDUCATIVO',sql.NVarChar,datos.nivelEducacion)
       .input('NUMERO_ARBOLES',sql.Int,datos.numeroArboles)
       .input('ENCARGADO',sql.NVarChar,datos.encargado)
       .input('ID_COL',sql.Int,datos.colonia)
       .query(`INSERT INTO ESCUELA(NOMBRE,DIRECCION,NIVEL_EDUCATIVO,NUMERO_ARBOLES,ENCARGADO,ID_COL,FECHA_REGISTRO) VALUES(@NOMBRE,@DIRECCION,@NIVEL_EDUCATIVO,@NUMERO_ARBOLES,@ENCARGADO,@ID_COL,(SELECT DBO.OBTENER_FECHA()))`)
       return true
     } catch (error) {
       throw error
     }
   },
 }

 const cuidados={
  agregar:async(datos)=>{
    try {
      await sqlConn.request()
      .input('ID_ARBOL',sql.Int,datos.id_arbol)
      .input('NOMBRE_CUIDADO',sql.NVarChar,datos.nombreCuidado)
      .input('DESCRIPCION',sql.NVarChar,datos.descripcion)
      .input('FRECUENCIA',sql.NVarChar,datos.frecuencia)
      .query(`INSERT INTO CUIDADOS(NOMBRE,DESCRIPCION,FRECUENCIA,ID_ARBOL) VALUES(@NOMBRE_CUIDADO,@DESCRIPCION,@FRECUENCIA,@ID_ARBOL)`)
    } catch (error) {
      throw error
    }
  },
 }

 const notificaciones={
   agregar:async(datos)=>{
     try {
       await sqlConn.request()
       .input('ID_US',sql.Int,datos.id_us)
       .input('MENSAJE',sql.NVarChar,datos.mensaje)
       .query(`EXEC ENVIAR_NOTIFICACION @ID_US,@MENSAJE`)
     } catch (error) {
       throw error
     }
   },
 }

module.exports={prueba,usuario,alumno,arbol,colonia,escuela,cuidados,notificaciones,app_movil}