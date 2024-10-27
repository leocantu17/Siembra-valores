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