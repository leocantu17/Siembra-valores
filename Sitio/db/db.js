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

module.exports={prueba}