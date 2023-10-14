package com.example.foodly.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Receta {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var nombre: String? = null
    var categoria: String? = null
    var ingredientes: String? = null
    var preparacion: String? = null
    var user: String? = null

    constructor(
        nombre: String?,
        categoria: String?,
        ingredientes: String?,
        preparacion: String?,
        user: String?
    ) {
        this.nombre = nombre
        this.categoria = categoria
        this.ingredientes = ingredientes
        this.preparacion = preparacion
        this.user = user
    }

    override fun toString(): String {
        return "Receta(id=$id, nombre=$nombre, categoria=$categoria, ingredientes=$ingredientes, preparacion=$preparacion, user=$user)"
    }


}