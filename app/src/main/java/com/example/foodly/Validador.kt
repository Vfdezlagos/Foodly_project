package com.example.foodly

import android.util.Patterns
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

}