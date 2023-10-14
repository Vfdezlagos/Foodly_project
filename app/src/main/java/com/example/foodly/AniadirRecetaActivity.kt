package com.example.foodly

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.foodly.roomdatabase.DB
import com.example.foodly.roomdatabase.entity.Receta
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class AniadirRecetaActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        private val CAMERA_PERMISSION_REQUEST_CODE = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aniadir_receta)

        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, DB.Db::class.java,"foodly-database").allowMainThreadQueries().build()

        val username = intent.getStringExtra("username")


        //REFERENCIAR WIDGETS
        val btn_aniadir = findViewById<Button>(R.id.btn_AniadirRec)
        val btn_volver = findViewById<Button>(R.id.btn_volverAniadirRec)
        val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasAniadirRec)
        val btn_foto = findViewById<Button>(R.id.btn_tomarFotoAniadirRec)



        //Asignar Categorias a Spinner
        val arrayAdapterSpinner: ArrayAdapter<*>

        val categorias = ArrayList<String>()
        categorias.add("Seleccione un categoría")
        categorias.add("vegetariano")
        categorias.add("vegano")
        categorias.add("carnes")
        categorias.add("postre")
        categorias.add("postre_vegano")

        arrayAdapterSpinner = ArrayAdapter(this@AniadirRecetaActivity, android.R.layout.simple_spinner_dropdown_item
            , categorias)

        sp_categorias.adapter = arrayAdapterSpinner



        //ACCION EVENTO CLICK
        btn_aniadir.setOnClickListener {

            //validar los campos

            val errores = validarCampos()

            if(errores == 0){

                val til_nombre_receta = findViewById<TextInputLayout>(R.id.til_nombreRecAniadirRec)
                val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasAniadirRec)
                val til_ingredientes = findViewById<TextInputLayout>(R.id.til_ingredientesAniadirRec)
                val til_preparacion = findViewById<TextInputLayout>(R.id.til_preparacionAniadirRec)

                val nombre = til_nombre_receta.editText?.text.toString()
                val ingredientes = til_ingredientes.editText?.text.toString()
                val preparacion = til_preparacion.editText?.text.toString()
                val categoria = sp_categorias.selectedItem.toString()

                val validador = Validador()

                //obtener recetas del usuario
                val recetas = room.daoReceta().obtenerRecetasUsuario(username)

                //validar que no exista la receta con ese nombre para el usuario determinado
                if(validador.validarRecetaExistente(recetas, nombre)){
                    til_nombre_receta.error = "La receta ya existe"
                }else{
                    til_nombre_receta.error = ""

                    val receta = Receta(nombre, categoria,  ingredientes, preparacion, username)

                    lifecycleScope.launch{
                        //registrar receta
                        val id = room.daoReceta().agregarReceta(receta)
                        //OPCIONAL VER INFORMACION de la receta añadida en log
                        val respuesta = room.daoReceta().obtenerRecetaNombre(username, nombre)
                        for (elemento in respuesta){
                            println(elemento.toString())
                        }
                        if(id>0){
                            Log.d("IDreceta",id.toString())
                            Toast.makeText(this@AniadirRecetaActivity, "Receta guardada", Toast.LENGTH_SHORT).show()
                        }

                        val intent = Intent(this@AniadirRecetaActivity, HomeActivity::class.java)

                        intent.putExtra("username", username)

                        startActivity(intent)
                    }
                }
            }

        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@AniadirRecetaActivity, HomeActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        btn_foto.setOnClickListener {
            checkCameraPermission()
        }
    }



    fun validarCampos() :Int{

        var contador:Int = 0

        val validador = Validador()

        //Captura de datos
        val til_nombre_receta = findViewById<TextInputLayout>(R.id.til_nombreRecAniadirRec)
        val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasAniadirRec)
        val til_ingredientes = findViewById<TextInputLayout>(R.id.til_ingredientesAniadirRec)
        val til_preparacion = findViewById<TextInputLayout>(R.id.til_preparacionAniadirRec)

        val nombre = til_nombre_receta.editText?.text.toString()
        val ingredientes = til_ingredientes.editText?.text.toString()
        val preparacion = til_preparacion.editText?.text.toString()
        val categoria = sp_categorias.selectedItem.toString()


        //Validar nombre
        if(validador.validarCampoVacio(nombre)){
            til_nombre_receta.error = "Este campo es obligatorio"
            contador++
        }else{
            til_nombre_receta.error = ""
        }


        //Validar categoria
        if(categoria == "Seleccione un categoría"){
            Toast.makeText(this, "Debes seleccionar una categoría", Toast.LENGTH_SHORT).show()
            contador++
        }


        //Validar ingredientes
        if(validador.validarCampoVacio(ingredientes)){
            til_ingredientes.error = "Este campo es obligatorio"
            contador++
        }else{
            til_ingredientes.error = ""
        }


        //Validar ingredientes
        if(validador.validarCampoVacio(preparacion)){
            til_preparacion.error = "Este campo es obligatorio"
            contador++
        }else{
            til_preparacion.error = ""
        }





        return contador

    }


    //Funciones para utilizar la camara (permisos y captura de imagen)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso otorgado, inicia la cámara
                    dispatchTakePictureIntent()
                } else {
                    // Permiso denegado, muestra un mensaje o maneja según tus necesidades
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imv_foto = findViewById<ImageView>(R.id.imv_fotoRecetaAniadirRec)
            imv_foto.setImageBitmap(imageBitmap)
        }
    }

    // METODO QUE VALIDA EL PERMISO DE LA CAMARA EN CASO DE TENER PERMISO EJECUTARA EL INTENT DE LA FOTO
    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permiso
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // Si ya tienes el permiso, inicia la cámara
            dispatchTakePictureIntent()
        }
    }

    // METODO QUE GATILLARA LA CAPTURA DE LA IMAGEN
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


}