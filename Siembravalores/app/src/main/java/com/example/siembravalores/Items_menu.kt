package com.example.siembravalores

sealed class Items_menu(
    val icon: Int,
    val title:String,
    val ruta: String
){
    object Adoptar:Items_menu(R.drawable.adoptar,"Adoptar","Arboles")
    object misArboles:Items_menu(R.drawable.arboles,"Mis árboles","misArboles")
    object Perfil:Items_menu(R.drawable.perfil,"Perfil","Perfil")
    object Notificaciones:Items_menu(R.drawable.notificaciones,"Notificaciones","Notificaciones")

}