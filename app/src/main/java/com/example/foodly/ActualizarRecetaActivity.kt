package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.foodly.roomdatabase.DB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class ActualizarRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_receta)

        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, DB.Db::class.java,"foodly-database").allowMainThreadQueries().build()

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
        categorias.add("vegetariano")
        categorias.add("vegano")
        categorias.add("carnes")
        categorias.add("postre")
        categorias.add("postre_vegano")

        arrayAdapterSpinner = ArrayAdapter(this@ActualizarRecetaActivity, android.R.layout.simple_spinner_dropdown_item
            , categorias)

        sp_categorias.adapter = arrayAdapterSpinner




        //obtener id de receta y username
        val id = intent.getLongExtra("id", 0)
        val username = intent.getStringExtra("username")


        //obtener receta
        val receta = room.daoReceta().obtenerRecetaId(id)


        //obtener index de la categoria
        var index_categoria = 0

        when(receta.categoria){
            "Seleccione un categoría" -> index_categoria = 0
            "vegetariano" -> index_categoria = 1
            "vegano" -> index_categoria = 2
            "carnes" -> index_categoria = 3
            "postre" -> index_categoria = 4
            "postre_vegano" -> index_categoria = 5
            else -> index_categoria = 0
        }

        //setar nombre receta
        til_nombre.editText?.setText(receta.nombre)

        //setear categoria de la receta
        sp_categorias.setSelection(index_categoria)

        //setear ingredientes receta
        til_ingredientes.editText?.setText(receta.ingredientes)

        //setear preparacion de la receta
        til_preparacion.editText?.setText(receta.preparacion)



        //ACCION EVENTO CLICK
        btn_actualizar.setOnClickListener {

            //Validar los campos

            val errores = validarCampos()

            if(errores == 0){

                //Captura de datos
                val til_nombre_receta = findViewById<TextInputLayout>(R.id.til_nombreRecActualizarRec)
                val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasActualizarRec)
                val til_ingredientes = findViewById<TextInputLayout>(R.id.til_ingredientesActualizarRec)
                val til_preparacion = findViewById<TextInputLayout>(R.id.til_preparacionActualizarRec)

                val nombre = til_nombre_receta.editText?.text.toString()
                val ingredientes = til_ingredientes.editText?.text.toString()
                val preparacion = til_preparacion.editText?.text.toString()
                val categoria = sp_categorias.selectedItem.toString()

                val validador = Validador()

                //obtener recetas del usuario
                val recetas = room.daoReceta().obtenerRecetasUsuario(username)

                //validar que no exista la receta con ese nombre para el usuario determinado
                if(validador.validarRecetaExistenteActualizar(recetas, nombre, id)){
                    til_nombre.error = "La receta ya existe"
                }else{
                    til_nombre.error = ""
                    lifecycleScope.launch {
                        room.daoReceta().actualizarReceta(nombre, categoria, ingredientes, preparacion, id)

                        val intent = Intent(this@ActualizarRecetaActivity, DetalleRecetaActivity::class.java)

                        //para que al actualizar se muestren los resultados actualizados en el detalle de la receta (provisorio luego sera con DB)
                        intent.putExtra("id", id)
                        intent.putExtra("username", username)

                        Toast.makeText(this@ActualizarRecetaActivity, "Receta Actualizada", Toast.LENGTH_SHORT).show()

                        startActivity(intent)
                    }

                }

            }

        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@ActualizarRecetaActivity, DetalleRecetaActivity::class.java)

            //Pasar valores a detalle activity
            intent.putExtra("id", id)
            intent.putExtra("username", username)

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