package com.example.foodly.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodly.roomdatabase.entity.Receta

@Dao
interface DaoReceta {

    //Listar todas las recetas del usuario
    @Query("SELECT * FROM Receta WHERE user=:username")
    fun obtenerRecetasUsuario(username: String?): List<Receta>

    //Listar todas las recetas del usuario y de la categoria seleccionada
    @Query("SELECT * FROM Receta WHERE user=:username AND categoria=:categoria")
    fun obtenerRecetasUsuarioCategoria(username: String?, categoria: String): List<Receta>

    //Listar Receta por nombre
    @Query("SELECT * FROM Receta WHERE user=:username AND nombre=:nombre")
    fun obtenerRecetaNombre(username: String?, nombre: String?): List<Receta>

    //Listar receta por id
    @Query("SELECT * FROM Receta WHERE id=:id")
    fun obtenerRecetaId(id: Long): Receta

    //Añadir receta
    @Insert
    fun agregarReceta(receta: Receta):Long

    //Actualizar Receta
    @Query("UPDATE  Receta SET nombre=:nombre,categoria=:categoria,ingredientes=:ingredientes,preparacion=:preparacion WHERE id=:id")
    fun actualizarReceta(nombre:String,categoria: String,ingredientes:String,preparacion:String,id:Long): Int

    //Eliminar receta
    @Query("DELETE FROM Receta WHERE id=:id")
    fun eliminarReceta(id: Long)

}