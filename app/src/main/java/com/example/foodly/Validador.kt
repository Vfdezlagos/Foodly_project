package com.example.foodly

import android.util.Patterns
import com.example.foodly.roomdatabase.entity.Receta
import com.example.foodly.roomdatabase.entity.Usuario
import java.util.regex.Pattern

class Validador {

    //Contendrá todas las validaciones necesarias para la aplicacion

    fun validarCampoVacio(texto: String): Boolean{
        // Retorna true si el texto es un string vacio o si su longitud es 0
        return texto.trim().equals("") || texto.trim().length==0
    }

    fun validarCamposIguales(texto: String,texto2: String): Boolean{
        // Retorna true si los textos son distintos
        return !texto.trim().equals(texto2.trim())
    }

    fun validarUsername(texto: String): Boolean{
        //Retorna true si el texto NO coincide con el patron y/o la cantidad de caracteres (min 8)

        //el patron evalua si el username comienza con una letra (minuscula o mayuscula).
        //el username puede o no tener numeros.
        val pattern = Pattern.compile("^([a-zA-Z])([a-zA-Z0-9]+)?")

        return !pattern.matcher(texto).matches() || texto.trim().length < 8
    }

    fun validarEmail(texto: String): Boolean{
        //Retorna true si el correo no es valido.
        return !Patterns.EMAIL_ADDRESS.matcher(texto).matches()
    }

    fun validarFecha(fecha_actual: Long, fecha_seleccionada: Long): Boolean{
        //Retorna true si la fecha actual es menor a la fecha seleccionada
        return fecha_actual < fecha_seleccionada
    }

    fun validarUsuarioRegistrado(usuarios: List<Usuario>, username: String, password:String): Boolean {

        //Retorna true si el username o password son incorrectos.

        for(usuario in usuarios){
            if(usuario.username.equals(username) && usuario.password.equals(password)){
                return false
            }
        }

        return true
    }

    fun validarUsuarioExistente(usuarios: List<Usuario>, username: String, email: String): Boolean{

        //Retorna true si el usuario o correo ya estan registrados en la DB

        for(usuario in usuarios){
            if(usuario.username.equals(username) || usuario.email.equals(email)){
                return true
            }
        }

        return false
    }

    fun validarContraseñaUsuario(usuario: Usuario, password: String): Boolean{
        //Retorna true si la pass del usuario es distinta a la pass entregada
        return !usuario.password.equals(password)
    }

    fun validarRecetaExistente(recetas: List<Receta>, nombre: String): Boolean{

        //Retorna true si la receta ya existe y no tiene el mismo id

        for (receta in recetas){
            if(receta.nombre.equals(nombre)){
                return true
            }
        }
        return false
    }

    fun validarRecetaExistenteActualizar(recetas: List<Receta>, nombre: String, id: Long): Boolean{

        //Retorna true si la receta ya existe y no tiene el mismo id

        for (receta in recetas){
            if(receta.nombre.equals(nombre) && !receta.id.equals(id)){
                return true
            }
        }
        return false
    }
}