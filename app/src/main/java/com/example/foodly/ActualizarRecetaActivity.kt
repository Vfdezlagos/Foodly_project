package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class ActualizarRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_receta)

        //REFERENCIAR WIDGETS
        val btn_actualizar = findViewById<Button>(R.id.btn_ActualizarRec)
        val btn_volver = findViewById<Button>(R.id.btn_volverActualizarRec)
        val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasActualizarRec)
        val til_nombre = findViewById<TextInputLayout>(R.id.til_nombreRecActualizarRec)
        val til_ingredientes = findViewById<TextInputLayout>(R.id.til_ingredientesActualizarRec)
        val til_preparacion = findViewById<TextInputLayout>(R.id.til_preparacionActualizarRec)



        //Asignar Categorias a Spinner
        val arrayAdapterSpinner: ArrayAdapter<*>

        val categorias = ArrayList<String>()
        categorias.add("Seleccione un categoría")
        categorias.add("Vegetariano")
        categorias.add("Vegano")
        categorias.add("Carnes")
        categorias.add("Postre")
        categorias.add("Postre Vegano")

        arrayAdapterSpinner = ArrayAdapter(this@ActualizarRecetaActivity, android.R.layout.simple_spinner_dropdown_item
            , categorias)

        sp_categorias.adapter = arrayAdapterSpinner



        //Obtener datos del intent
        val index_receta = intent.getIntExtra("index_receta", 0)
        val index_categoria = intent.getIntExtra("index_categoria", 0)
        val nombre_receta = intent.getStringExtra("nombre_receta")
        val ingredientes = intent.getStringExtra("ingredientes_receta")
        val preparacion = intent.getStringExtra("preparacion_receta")



        //setar nombre receta
        til_nombre.editText?.setText(nombre_receta)

        //setear categoria de la receta
        sp_categorias.setSelection(index_categoria)

        //setear ingredientes receta
        til_ingredientes.editText?.setText(ingredientes)

        //setear preparacion de la receta
        til_preparacion.editText?.setText(preparacion)



        //ACCION EVENTO CLICK
        btn_actualizar.setOnClickListener {

            //Validar los campos

            val errores = validarCampos()

            if(errores == 0){
                val intent = Intent(this@ActualizarRecetaActivity, DetalleRecetaActivity::class.java)

                //para que al actualizar se muestren los resultados actualizados en el detalle de la receta (provisorio luego sera con DB)
                intent.putExtra("index_receta", index_receta)
                intent.putExtra("index_categoria", sp_categorias.selectedItemPosition)
                intent.putExtra("nombre_receta", til_nombre.editText?.text.toString())
                intent.putExtra("ingredientes_receta", til_ingredientes.editText?.text.toString())
                intent.putExtra("preparacion_receta", til_preparacion.editText?.text.toString())

                Toast.makeText(this, "Receta Actualizada", Toast.LENGTH_SHORT).show()

                startActivity(intent)
            }

        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@ActualizarRecetaActivity, DetalleRecetaActivity::class.java)

            //Pasar valores a detalle activity
            intent.putExtra("index_receta", index_receta)
            intent.putExtra("index_categoria", index_categoria)
            intent.putExtra("nombre_receta", nombre_receta)
            intent.putExtra("ingredientes_receta", ingredientes)
            intent.putExtra("preparacion_receta", preparacion)

            startActivity(intent)
        }
    }



    fun validarCampos() :Int{

        var contador = 0

        val validador = Validador()

        //Captura de datos
        val til_nombre_receta = findViewById<TextInputLayout>(R.id.til_nombreRecActualizarRec)
        val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasActualizarRec)
        val til_ingredientes = findViewById<TextInputLayout>(R.id.til_ingredientesActualizarRec)
        val til_preparacion = findViewById<TextInputLayout>(R.id.til_preparacionActualizarRec)

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

}