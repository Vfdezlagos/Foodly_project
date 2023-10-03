package com.example.foodly

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //REFERENCIAR LOS WIDGET O UI CONTROL

        val btn_registrarse = findViewById<Button>(R.id.btn_registrarseMain)
        val btn_iniciarsesion = findViewById<Button>(R.id.btn_iniciarSesionMain)
        val til_username = findViewById<TextInputLayout>(R.id.til_usernameMain)
        val cb_recordar_usuario = findViewById<CheckBox>(R.id.cb_recordarUsuarioMain)



        //Recordar usuario
        val preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        til_username.editText?.setText(preferencias.getString("username", ""))



        //ACCION DEL METODO CLICK
        btn_iniciarsesion.setOnClickListener {
            //VALIDAR LOS CAMPOS
            val errores = validarCampos()

            if(errores == 0){
                //Validacion checkbox recordar usuario
                val editor = preferencias.edit()

                if(cb_recordar_usuario.isChecked){
                    editor.putString("username", til_username.editText?.text.toString())
                    editor.commit()
                    Toast.makeText(this@MainActivity, "Usuario recordado", Toast.LENGTH_SHORT).show()
                }else{
                    editor.putString("username", "")
                    editor.commit()
                }

                //REDIRECCIONAR A HOME ACTIVITY
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
            }

        }

        btn_registrarse.setOnClickListener {
            //REDIRECCIONAR A REGISTRO USUARIO ACTIVITY
            val intent = Intent(this@MainActivity, RegistroUsuarioActivity::class.java)
            startActivity(intent)
        }


    }



    fun validarCampos() :Int{

        var contador:Int = 0

        val validador = Validador()

        //Captura de datos
        val til_username = findViewById<TextInputLayout>(R.id.til_usernameMain)
        val til_password = findViewById<TextInputLayout>(R.id.til_passwordMain)
        val username = til_username.editText?.text.toString()
        val password = til_password.editText?.text.toString()


        //Validacion username

        //Validacion de campo vacio
        if(validador.validarCampoVacio(username)){
            til_username.error = "Este campo es obligatorio"
            contador++
        }else{
            til_username.error = ""

            //Validacion de patron de username
            if(validador.validarUsername(username)){
                til_username.error = "Debe tener al menos 8 Caracteres y comenzar con una letra"
                contador++
            }else{
                til_username.error = ""
            }
        }

        //Validacion Password

        //Validacion de campo vacio
        if(validador.validarCampoVacio(password)){
            til_password.error = "Este campo es obligatorio"
            contador++
        }else{
            til_password.error = ""
        }


        return contador

    }
}