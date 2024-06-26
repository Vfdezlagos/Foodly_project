package com.example.foodly

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.foodly.roomdatabase.DB
import com.example.foodly.roomdatabase.entity.Usuario
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.util.Calendar

class RegistroUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, DB.Db::class.java,"foodly-database").allowMainThreadQueries().build()


        //REFERENCIAR WIDGETS
        val btn_registrar = findViewById<Button>(R.id.btn_registrarRegistroUsu)
        val btn_volver = findViewById<Button>(R.id.btn_volverRegistroUsu)
        val til_datepicker = findViewById<TextInputLayout>(R.id.til_datepickerRegistroUsu)
        val til_username = findViewById<TextInputLayout>(R.id.til_usernameRegistroUsu)
        val til_email = findViewById<TextInputLayout>(R.id.til_emailRegistroUsu)
        val til_password = findViewById<TextInputLayout>(R.id.til_passwordRegistroUsu)
        val cal = Calendar.getInstance()



        //DATEPICKER

        //listener datepicker
        val listenerFecha = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
            //correcion de mes.
            val month = month + 1
            til_datepicker.editText?.setText("$dayOfMonth/$month/$year")
        }

        //ACCION CLICK til_datepicker
        til_datepicker.editText?.setOnClickListener {
            //Mostrar datepicker
            DatePickerDialog(this@RegistroUsuarioActivity, listenerFecha, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }



        //ACCION DEL EVENTO CLICK BOTONES
        btn_registrar.setOnClickListener {

            //Validar los campos
            val errores = validarCampos()

            if(errores == 0){

                //obtenemos los valores de los inputs
                val username = til_username.editText?.text.toString()
                val email = til_email.editText?.text.toString()
                val password = til_password.editText?.text.toString()
                val fecha_selec = til_datepicker.editText?.text.toString()

                //Listamos los usuarios de la DB
                val usuarios = room.daoUsuario().obtenerUsuarios()

                //creamos el objeto validador
                val validador = Validador()

                //verificar que el username no este ocupado
                if(validador.validarUsuarioExistente(usuarios, username, email)){
                    til_username.error = "Username ya existente"
                    til_email.error = "Correo ya registrado"
                    Toast.makeText(this@RegistroUsuarioActivity, "Username o Email ya registrado", Toast.LENGTH_SHORT).show()
                }else{
                    til_username.error = ""
                    til_email.error = ""

                    //Creamos al usuario como un objeto Usuario
                    val usuario = Usuario(username, email, fecha_selec, password)
                    lifecycleScope.launch{

                        //registrar usuario en db
                        val id = room.daoUsuario().agregarUsuario(usuario)

                        //verificar registro
                        if(id>0){
                            Log.d("IDuser",id.toString())

                            val intent = Intent(this@RegistroUsuarioActivity, MainActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this@RegistroUsuarioActivity,"Usuario registrado exitosamente",Toast.LENGTH_LONG).show()
                        }
                    }
                }
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
        val til_datepicker = findViewById<TextInputLayout>(R.id.til_datepickerRegistroUsu)

        val username = til_username.editText?.text.toString()
        val email = til_email.editText?.text.toString()
        val password = til_password.editText?.text.toString()
        val rep_password = til_rep_password.editText?.text.toString()
        val fecha_selec = til_datepicker.editText?.text.toString()


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



        //Validacion Fecha nacimiento

        //Validacion campo vacio
        if(validador.validarCampoVacio(fecha_selec)){
            til_datepicker.error = "Este campo es obligatorio"
            contador++
        }else{
            til_datepicker.error = ""

            //Validacion fecha menor o igual a la actual
            val cal = Calendar.getInstance()
            val formatter = SimpleDateFormat("dd/MM/yyyy")

            val fecha_actual_millis = cal.timeInMillis

            //setear y formatear fecha seleccionada
            cal.setTime(formatter.parse(fecha_selec))

            val fecha_selec_millis = cal.timeInMillis


            if(validador.validarFecha(fecha_actual_millis, fecha_selec_millis)){
                til_datepicker.error = "Fecha inválida"
                contador++
            }else{
                til_datepicker.error = ""
            }
        }


        return contador

    }
}