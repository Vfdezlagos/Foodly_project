package com.example.foodly.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodly.roomdatabase.dao.DaoReceta
import com.example.foodly.roomdatabase.dao.DaoUsuario
import com.example.foodly.roomdatabase.entity.Receta
import com.example.foodly.roomdatabase.entity.Usuario


class DB {
    @Database(
        entities = [Usuario::class,Receta::class],
        version = 1
    )
    abstract class Db: RoomDatabase(){
        abstract fun daoUsuario(): DaoUsuario
        abstract fun daoReceta(): DaoReceta
    }

}