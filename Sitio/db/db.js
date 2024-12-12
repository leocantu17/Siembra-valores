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

 const appMovilDb={
  inicirSesionApp:async(data)=>{
    try {
      const resultado=await sqlConn.request()
      .input(`CORREO`,sql.NVarChar,data.correo)
      .input(`CONTRASENA`,sql.NVarChar,data.contrasena)
      .query(`SELECT A.ID_US AS id FROM USUARIO U JOIN ALUMNO A ON U.ID_US=A.ID_US WHERE U.CORREO=@CORREO AND U.CONTRASENA=@CONTRASENA`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
  },
  arbolesDb:async(datos)=>{
    try {
        const resultado=await sqlConn.request()
        .input("ID_US",sql.Int,datos.id)
        .query(`SELECT A.DESCRIPCION,
                A.ID_ARBOL AS ID,
	              CASE
	              WHEN A.ID_VALOR IS NULL THEN  A.NOMBRE_CIENTIFICO
	              ELSE V.VALOR 
	              END AS NOMBRE
                FROM ARBOL A LEFT JOIN VALORES V ON A.ID_VALOR=V.ID_VALOR  `)
                // WHERE A.ID_COL=(SELECT ID_COL FROM USUARIO WHERE ID_US=@ID_US)
        return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
  },
  informacionArbolDb:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .input(`ID_ARBOL_INFO`,sql.Int,datos.id)
      .query(`DECLARE @ID_VALOR_INFO INT;
              SET @ID_VALOR_INFO=(SELECT ID_VALOR FROM ARBOL WHERE ID_ARBOL=@ID_ARBOL_INFO);
              IF @ID_VALOR_INFO IS NULL
              BEGIN
                SELECT CO.NOMBRE AS COLONIA,A.ID_ARBOL AS ID,A.NOMBRE_CIENTIFICO,FORMAT(A.FECHA_PLANTADO,'dd/MM/yy ') AS FECHA_PLANTADO,C.DESCRIPCION,C.FRECUENCIA,A.ID_VALOR,V.VALOR AS NOMBRE_VALOR FROM ARBOL A JOIN CUIDADOS C ON A.ID_ARBOL=C.ID_CUIDADOS LEFT JOIN VALORES V ON A.ID_VALOR=V.ID_VALOR JOIN COLONIA CO ON A.ID_COL=CO.ID_COL WHERE A.ID_ARBOL=@ID_ARBOL_INFO
              END
              ELSE
              BEGIN
                SELECT CO.NOMBRE AS COLONIA,A.ID_ARBOL AS ID,A.NOMBRE_CIENTIFICO,FORMAT(A.FECHA_PLANTADO,'dd/MM/yy ') AS FECHA_PLANTADO,C.DESCRIPCION,C.FRECUENCIA,A.ID_VALOR,V.VALOR AS NOMBRE_VALOR FROM ARBOL A JOIN CUIDADOS C ON A.ID_ARBOL=C.ID_CUIDADOS LEFT JOIN VALORES V ON A.ID_VALOR=V.ID_VALOR JOIN COLONIA CO ON A.ID_COL=CO.ID_COL WHERE A.ID_ARBOL=@ID_ARBOL_INFO
              END`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
  },
  adoptarArbolApp:async(datos)=>{
    try {
      await sqlConn.request()
      .input('ID_US',sql.Int,datos.ID_US)
      .input('ID_ARBOL',sql.Int,datos.ID_ARBOL)
      .input('ID_VALOR',sql.Int,datos.ID_VALOR)
      .query(`EXEC ADOPTA_ARBOL @ID_US,@ID_ARBOL,@ID_VALOR`)
    } catch (error) {
      throw error
    }
  },
  historialServiciosDb:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .input('ID_US',sql.Int,datos.id)
      .query(`SELECT S.TIPO,FORMAT(B.FECHA_SERVICIO,'dd/MM/yy') AS FECHA_SERVICIO,B.COMENTARIOS FROM SERVICIOS S JOIN BRINDAN B ON S.ID_SERVICIO=B.ID_SERVICIO JOIN ARBOL A
              ON A.ID_ARBOL=B.ID_ARBOL JOIN ADOPTA AD ON B.ID_ARBOL=AD.ID_ARBOL WHERE AD.ID_US=@ID_US`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
  },
  perfilUsuarioDb:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .input('ID_US',sql.Int,datos.id)
      .query(`SELECT A.PUNTOS,U.NOMBRE,U.AP_P,U.AP_M,U.CORREO,U.FECHA_NAC FROM USUARIO U JOIN ALUMNO A ON U.ID_US=A.ID_US WHERE A.ID_US=@ID_US`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
  },
  realizarMisionDb:async(datos)=>{
    try {
      await sqlConn.request()
      .input('ID_US',sql.Int,datos.id_us)
      .input('PUNTOS',sql.Int,datos.puntos)
      .query(`UPDATE ALUMNO SET PUNTOS=@PUNTOS WHERE ID_US=@ID_US`)
    } catch (error) {
      throw error
    }
  },
  notificacionesAlumnoDb:async(datos)=>{
    try {
      const resultado=await sqlConn.request()
      .input('ID_US',sql.Int,datos.id_us)
      .query(`SELECT N.ID_NOTIFICACION,N.MENSAJE, N.FECHA_ENVIO,V.VALOR FROM NOTIFICACIONES N JOIN  ADOPTA AD ON N.ID_ARBOL = AD.ID_ARBOL
            JOIN ARBOL A ON AD.ID_ARBOL = A.ID_ARBOL JOIN VALOR V ON A.ID_VALOR=V.ID_VALOR  WHERE N.ID_US = @ID_US AND N.LEIDO = 0  ORDER BY N.FECHA_ENVIO DESC;`)
      return objetoResultadoSQL(resultado)
    } catch (error) {
      throw error
    }
 },
 notifciacionesLeidoDb:async(datos)=>{
   try {
     await sqlConn.request()
     .input('ID_NOTIFICACION',sql.Int,datos.id)
     .query(`UPDATE NOTIFICACIONES SET LEIDO=1 WHERE ID_NOTIFICACION=@ID_NOTIFICACION`)
   } catch (error) {
     throw error
   }
 },
 valoresDb:async()=>{
  try {
    const resultado=await sqlConn.request()
    .query("SELECT * FROM VALORES")
    return objetoResultadoSQL(resultado)
  } catch (error) {
    throw error
  }
 },
 misArbolesDbInfo:async(datos)=>{
  try {
    const resultado=await sqlConn.request()
    .input('ID_ARBOL',sql.Int,datos.ID_ARBOL)
    .query(`SELECT A.ID_ARBOL, A.NOMBRE_CIENTIFICO,A.DESCRIPCION,A.ENDEMICO,FORMAT(AD.FECHA_ADOPCION_INICIO,'dd/MM/yy ') AS FECHA_PLANTADO,AD.ALTURA,AD.CIRCUNFERENCIA,
    V.VALOR,C.NOMBRE AS COLONIA FROM ADOPTA AD JOIN ARBOL A ON AD.ID_ARBOL = A.ID_ARBOL LEFT JOIN  VALORES V ON A.ID_VALOR = V.ID_VALOR LEFT JOIN COLONIA C ON A.ID_COL = C.ID_COL
    WHERE AD.ID_ARBOL = @ID_ARBOL `)
    return objetoResultadoSQL(resultado)
  } catch (error) {
    throw error
  }
 },
 misArbolesDb:async(datos)=>{
  try {
    const resultado=await sqlConn.request()
    .input('ID_US',sql.Int,datos.ID_US)
    .query(`SELECT V.VALOR AS NOMBRE_VALOR,A.DESCRIPCION,A.ID_ARBOL FROM ARBOL A LEFT JOIN VALORES V ON A.ID_VALOR=V.ID_VALOR LEFT JOIN ADOPTA AD ON AD.ID_ARBOL=A.ID_ARBOL WHERE AD.ID_US=@ID_US`)
    return objetoResultadoSQL(resultado)
  } catch (error) {
    throw error
  }
},
serviciosDb:async()=>{ 
  try {
    const resultado=await sqlConn.request()
    .query(`SELECT * FROM SERVICIOS `)
    return objetoResultadoSQL(resultado)
  } catch (error) {
    throw error
  }
},
perfilUsuarioDb:async(datos)=>{
  try {
    const resultado=await sqlConn.request()
    .input('ID_US',sql.Int,datos.ID_US)
    .query(`SELECT A.PUNTOS,U.NOMBRE,U.AP_P,U.AP_M,U.CORREO,FORMAT(U.FECHA_NAC,'dd/MM/yy') AS FECHA_NAC,FORMAT(U.FECHA_REGISTRO,'dd/MM/yy') AS FECHA_REGISTRO,E.NOMBRE AS ESCUELA,U.CELULAR
      FROM USUARIO U JOIN ALUMNO A ON U.ID_US=A.ID_US JOIN ESCUELA E ON A.ID_ESCUELA=E.ID_ESCUELA WHERE A.ID_US=@ID_US`)
    return objetoResultadoSQL(resultado)
  } catch (error) {
    throw error
  }
},
agregarNotificacionDb:async(datos)=>{
  try {
    await sqlConn.request()
    .query(`INSERT INTO NOTIFICACIONES (ID_US, ID_ARBOL, MENSAJE, FECHA_ENVIO)
            SELECT 
                A.ID_US,
                A.ID_ARBOL,
                CONCAT('Es hora de realizar el cuidado: ', C.NOMBRE, ' para el árbol adoptado.'),
                (SELECT DBO.OBTENER_FECHA())
            FROM 
                ADOPTA A
            JOIN 
                CUIDADOS C ON A.ID_ARBOL = C.ID_CUIDADOS
            LEFT JOIN 
                BRINDAN B ON A.ID_ARBOL = B.ID_ARBOL AND C.ID_CUIDADOS = B.ID_SERVICIO
            WHERE 
                DATEDIFF(DAY, B.FECHA_SERVICIO, (SELECT DBO.OBTENER_FECHA())) >= 
                CASE 
                    WHEN C.FRECUENCIA = 'Semanal' THEN 7
                    WHEN C.FRECUENCIA = 'Quincenal' THEN 15
                    WHEN C.FRECUENCIA = 'Mensual' THEN 30
                    WHEN C.FRECUENCIA = 'Bimensual' THEN 60
                    WHEN C.FRECUENCIA = 'Trimestral' THEN 90
                    WHEN C.FRECUENCIA = 'Semestral' THEN 180
                    WHEN C.FRECUENCIA = 'Anual' THEN 365
                    ELSE 0
                END;
`)
  } catch (error) {
    throw error
  }
},
agregarServicioDb:async(datos)=>{
  try { 
    await sqlConn.request()
    .input('ID_US',sql.Int,datos.ID_US)
    .input('TIPO',sql.NVarChar,datos.TIPO)
    .input('COMENTARIOS',sql.NVarChar,datos.COMENTARIOS)
    .input('ALTURA',sql.Float,datos.ALTURA)
    .input('CIRCUNFERENCIA',sql.Float,datos.CIRCUNFERENCIA)
    .input('ID_ARBOL',sql.Int,datos.ID_ARBOL)
    .input('ID_SERVICIO',sql.Int,datos.ID_SERVICIO)
    .query(`EXEC AGREGAR_SERVICIO @ID_ARBOL,@ID_SERVICIO,@COMENTARIOS,@ALTURA,@CIRCUNFERENCIA,@ID_US`)
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

module.exports={prueba,usuario,alumno,arbol,colonia,escuela,cuidados,notificaciones,appMovilDb}