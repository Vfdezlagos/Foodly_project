package com.example.foodly.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Usuario {
    @PrimaryKey()
    var username: String
    var email: String? = null
    var birthday: String? = null
    var password: String? = null

    constructor(username: String, email: String?, birthday: String?, password: String?) {
        this.username = username
        this.email = email
        this.birthday = birthday
        this.password = password
    }

    override fun toString(): String {
        return "Usuario(username=$username, email=$email, birthday=$birthday, password=$password)"
    }


}