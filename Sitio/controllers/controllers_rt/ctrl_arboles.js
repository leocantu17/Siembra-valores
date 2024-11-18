const { arbol, cuidados } = require("../../db/db")

const ctrl_arboles = {
    eliminar: async (req, res) => {
        try {
            const {id}=req.body
            await arbol.eliminar({ID_ARBOL:id})
            return res.json({estatus:"OK",mensaje:"Árbol eliminado"})
        } catch (error) {
            console.log(error)
        }
    },
    agregar: async (req, res) => {
        try {
            const datos = req.body;
            // Verificar si los campos principales están vacíos
            if (datos['nombre-cientifico'] == '' || datos['fecha-plantado'] == '' || datos['endemico'] == '' || datos['colonia'] == '') {
                return res.json({ estatus: "ERR", mensaje: "Tienes que completar todos los campos principales" });
            }else{
                // Comprobar si alguno de los elementos de los cuidados está vacío
            const nombreCuidados = datos['nombre-cuidado'];
            const descripciones = datos['descripcion'];
            const frecuencias = datos['frecuencia'];
            await arbol.agregar({ nombreCientifico: datos['nombre-cientifico'], fechaPlantado: datos['fecha-plantado'], endemico: datos['endemico'], colonia: datos['colonia'] });
            const id_arbol=(await arbol.id_arbol()).datos[0].ID_ARBOL;
            // Verificar que las listas de cuidados, descripciones y frecuencias no tengan campos vacíos
            for (let i = 0; i < nombreCuidados.length; i++) {
                if (nombreCuidados[i] == '' || descripciones[i] == '' || frecuencias[i] == '') {
                    return res.json({ estatus: "ERR", mensaje: "Todos los campos de los cuidados deben ser completados" });
                }else{
                    await cuidados.agregar({id_arbol,nombreCuidado:nombreCuidados[i],descripcion:descripciones[i],frecuencia:frecuencias[i]})
                }
            }
            
            return res.json({ estatus: "OK", mensaje: "Datos recibidos correctamente" });
            }
            
            
        
        } catch (error) {
            console.log(error);
            return res.json({ estatus: "ERR", mensaje: "Hubo un error en el servidor" });
        }
        
    },
}

module.exports = ctrl_arboles