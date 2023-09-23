package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

class RegistroUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        //REFERENCIAR WIDGETS
        val btn_registrar = findViewById<Button>(R.id.btn_registrarRegistroUsu)
        val btn_volver = findViewById<Button>(R.id.btn_volverRegistroUsu)


        //ACCION DEL EVENTO CLICK
        btn_registrar.setOnClickListener {

            //Validar los campos
            val errores = validarCampos()

            if(errores == 0){
                Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@RegistroUsuarioActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@RegistroUsuarioActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun validarCampos() :Int{

        var contador:Int = 0

        val validador = Validador()

        //Captura de datos
        val til_username = findViewById<TextInputLayout>(R.id.til_usernameRegistroUsu)
        val til_email = findViewById<TextInputLayout>(R.id.til_emailRegistroUsu)
        val til_password = findViewById<TextInputLayout>(R.id.til_passwordRegistroUsu)
        val til_rep_password = findViewById<TextInputLayout>(R.id.til_RepPasswordRegistroUsu)

        val username = til_username.editText?.text.toString()
        val email = til_email.editText?.text.toString()
        val password = til_password.editText?.text.toString()
        val rep_password = til_rep_password.editText?.text.toString()


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


        //Validacion Correo

        //Validacion de campo vacio
        if(validador.validarCampoVacio(email)){
            til_email.error = "Este campo es obligatorio"
            contador++
        }else{
            til_email.error = ""

            //Validacion de patron de email
            if(validador.validarEmail(email)){
                til_email.error = "El correo no es válido"
                contador++
            }else{
                til_email.error = ""
            }
        }


        //Validacion password

        //Validacion de campo vacio
        if(validador.validarCampoVacio(password)){
            til_password.error = "Este campo es obligatorio"
            contador++
        }else{
            til_password.error = ""
        }


        //Validacion Rep_password
        if(validador.validarCampoVacio(rep_password)){
            til_rep_password.error = "Este campo es obligatorio"
            contador++
        }else{
            til_rep_password.error = ""

            //Validacion igualdad con password
            if(validador.validarCamposIguales(password, rep_password)){
                til_rep_password.error = "Las contraseñas no coinciden"
                contador++
            }else{
                til_rep_password.error = ""
            }
        }

        return contador

    }
}