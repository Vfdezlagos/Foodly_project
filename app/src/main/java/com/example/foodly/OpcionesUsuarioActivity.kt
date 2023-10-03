package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class OpcionesUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_usuario)

        //REFERENCIAR WIDGETS
        val btn_cambiarPass = findViewById<Button>(R.id.btn_cambiarPasswordOpcUsuario)
        val btn_cerrarSesion = findViewById<Button>(R.id.btn_cerrarSesionOpcUsuario)
        val btn_volver = findViewById<Button>(R.id.btn_volverOpcUsuario)



        //ACCION EVENTO CLICK
        btn_cambiarPass.setOnClickListener {

            val errores = validarCampos()

            if(errores == 0){
                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@OpcionesUsuarioActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        btn_cerrarSesion.setOnClickListener {
            Toast.makeText(this, "Saliendo de la sesión", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@OpcionesUsuarioActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@OpcionesUsuarioActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }



    fun validarCampos() :Int{

        var contador:Int = 0

        val validador = Validador()

        //Captura de datos
        val til_password = findViewById<TextInputLayout>(R.id.til_passwordActualOpcUsuario)
        val til_password_nueva = findViewById<TextInputLayout>(R.id.til_passwordNuevaOpcUsuario)
        val til_rep_password_nueva = findViewById<TextInputLayout>(R.id.til_repPasswordNuevaOpcUsuario)

        val password = til_password.editText?.text.toString()
        val password_nueva = til_password_nueva.editText?.text.toString()
        val rep_password_nueva = til_rep_password_nueva.editText?.text.toString()



        //Validar Password actual
        if(validador.validarCampoVacio(password)){
            til_password.error = "Este campo es obligatorio"
            contador++
        }else{
            til_password.error = ""
        }


        //Validar Password nueva
        if(validador.validarCampoVacio(password_nueva)){
            til_password_nueva.error = "Este campo es obligatorio"
            contador++
        }else{
            til_password_nueva.error = ""
        }


        //Validar Rep_password_nueva
        if(validador.validarCampoVacio(rep_password_nueva)){
            til_rep_password_nueva.error = "Este campo es obligatorio"
            contador++
        }else{
            til_rep_password_nueva.error = ""

            if(validador.validarCamposIguales(rep_password_nueva, password_nueva)){
                til_rep_password_nueva.error = "Las contraseñas no coinciden"
                contador++
            }else{
                til_rep_password_nueva.error = ""
            }
        }

        return contador

    }
}