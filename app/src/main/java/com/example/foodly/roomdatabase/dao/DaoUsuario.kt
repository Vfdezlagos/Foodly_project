package com.example.foodly.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodly.roomdatabase.entity.Usuario

@Dao
interface DaoUsuario {

    //funcion para listar todos los usuarios de la DB
    @Query("SELECT * FROM Usuario")
    fun obtenerUsuarios(): List<Usuario>

    //obtener usuario por su username
    @Query("SELECT * FROM Usuario WHERE username=:username")
    fun obtenerUsuario(username: String?): Usuario

    //obtener usuario por si su username y password coinciden
    @Query("SELECT * FROM Usuario WHERE username=:username AND password=:password")
    fun login(username: String, password: String): List<Usuario>

    //Registrar usuario en db
    @Insert
    fun agregarUsuario(usuario: Usuario):Long

    //actualizar contraseña de usuario dependiendo del username
    @Query("UPDATE  Usuario SET password=:password WHERE username=:username")
    fun actualizarUsuario(username: String, password:String): Int

}