package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class AniadirRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aniadir_receta)

        //REFERENCIAR WIDGETS
        val btn_aniadir = findViewById<Button>(R.id.btn_AniadirRec)
        val btn_volver = findViewById<Button>(R.id.btn_volverAniadirRec)

        //ACCION EVENTO CLICK
        btn_aniadir.setOnClickListener {

            //validar los campos

            val errores = validarCampos()

            if(errores == 0){
                Toast.makeText(this, "Receta guardada", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@AniadirRecetaActivity, HomeActivity::class.java)
                startActivity(intent)
            }

        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@AniadirRecetaActivity, HomeActivity::class.java)
            startActivity(intent)
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
        if(categoria == "Categoría"){
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

}