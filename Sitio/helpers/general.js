const hbs = require('hbs')

hbs.registerHelper('columnas',(tabla, orden)=>{
    let columnas=[]
    let html='';
    switch(tabla){
        case 'alumnos':
            columnas.push({nombre:'ID', id:'idUsu'},{nombre:'Nombre', id: 'nombreUsu'},
                          {nombre:'Correo', id: 'direccionUsu'},{nombre:'Fecha de alta', id: 'fechaUsu'},
                          {nombre:'Nombre de la escuela', id: 'nombreEsc'},{nombre:'Activo', id: 'estatusUsu'})
        break;
        case 'escuelas':
            columnas.push({nombre:'ID', id:'idEsc'},{nombre:'Nombre', id: 'nombreEsc'},
                          {nombre:'Nivel educativo', id: 'nivelEdu'},{nombre:'Fecha de alta', id: 'fechaEsc'},
                          {nombre:'Nombre de la colonia', id: 'nombreCol'},{nombre:'Activo', id: 'estatusEsc'})
        break;
        case 'colonias':
            columnas.push({nombre:'ID', id:'idCol'},{nombre:'Nombre', id: 'nombreCol'},
                          {nombre:'Ciudad', id: 'ciudad'},{nombre:'Codigo postal', id: 'codPostal'},{nombre:'Fecha de alta', id: 'fechaCol'},
                          {nombre:'Meta', id: 'meta'},{nombre:'Mínimo de arboles', id: 'minimoArboles'},{nombre:'Activo', id: 'estatusCol'})
        break;
        default:
            columnas.push({nombre:'ID', id:'idArbol'},{nombre:'Nombre científico', id: 'nombreCien'},
            {nombre:'Valor', id:'valor'},{nombre:'Nombre de la colonia'},{nombre:'Fecha de alta'},{nombre:'Vivo'},)
        break
    }
    columnas.forEach((elemento)=>{
        html+=`
          <th >${elemento.nombre}</th>

        ` 
    })

    return html;
})

hbs.registerHelper('eq',(valor1,valor2)=>{
    if(valor1==valor2){
        return true
    }else{
        return false
    }
})

hbs.registerHelper('lon',(valor1,valor2)=>{
    const indice=(valor1+1)==valor2?true:false;
    return indice;
})

hbs.registerHelper('inc',(valor1)=>{
    return valor1+1
})