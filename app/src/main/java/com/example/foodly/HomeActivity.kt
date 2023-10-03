package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //REFERENCIAS WIDGETS
        val btn_opciones_usuario = findViewById<Button>(R.id.btn_opcionesUsuarioHome)
        val btn_aniadir_receta = findViewById<Button>(R.id.btn_aniadirRecHome)
        val lv_recetas = findViewById<ListView>(R.id.Lv_recetasHome)
        val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasHome)



        //Asignar Categorias a Spinner
        val arrayAdapterSpinner: ArrayAdapter<*>
        val categorias = ArrayList<String>()



        //Insertar categorias al array
        categorias.add("Seleccione un categoría")
        categorias.add("Vegetariano")
        categorias.add("Vegano")
        categorias.add("Carnes")
        categorias.add("Postre")
        categorias.add("Postre Vegano")

        arrayAdapterSpinner = ArrayAdapter(this@HomeActivity, android.R.layout.simple_spinner_dropdown_item
            , categorias)

        sp_categorias.adapter = arrayAdapterSpinner



        // Asignar elementos al ListView (Recetas)

        //creacion del array de nombres_recetas
        val nombres_recetas = ArrayList<String>()

        //Insertar nombres al array
        nombres_recetas.add("Bizcocho Casero")
        nombres_recetas.add("Ají de gallina")
        nombres_recetas.add("Hummus tradicional")
        nombres_recetas.add("Carne mongoliana")

        //agregar elementos del array al listview
        val arrayAdapterListView: ArrayAdapter<*>
        arrayAdapterListView = ArrayAdapter(this@HomeActivity, android.R.layout.simple_selectable_list_item, nombres_recetas)

        lv_recetas.adapter = arrayAdapterListView



        //Asignar categoria a las recetas
        val categoria_receta = ArrayList<Int>()

        categoria_receta.add(4)
        categoria_receta.add(3)
        categoria_receta.add(2)
        categoria_receta.add(3)



        //Ingredientes Y Preparaciones de recetas (ARRAYS)(Provisorios)

        //ArrayList con ingredientes
        val ingredientes = ArrayList<String>()

        ingredientes.add("- 4 huevos.\n\n" +
                "- 120gr azucar.\n\n" +
                "- 120g de harina.")

        ingredientes.add("- 1 cebolla picada\n\n" +
                "- 2 Pechugas de pollo (gallina de preferencia)\n\n" +
                "- 2 dientes de ajo picados\n\n" +
                "- Apio y zanahoria en trozos\n\n" +
                "- Sal, pimienta y comino a gusto\n\n" +
                "- 3 cucharadas aceite vegetal\n\n" +
                " -Leche Evaporada\n\n" +
                "- 4 rebanadas de pan de molde sin bordes\n\n" +
                "- 5 cucharadas ají amarillo molido\n\n" +
                "- 2 cucharadas ají mirasol molido\n\n" +
                "- Si lo deseamos, podemos además usar nueces.")

        ingredientes.add("- 400g de garbanzos\n\n" +
                "- 1 diente de ajo\n\n" +
                "- 2 cucharadas de aceite de oliva\n\n" +
                "- 1/2 cucharadita de comino en polvo\n\n" +
                "- 1 cucharadita de pasta tahini\n\n" +
                "- 1 cucharadita de pimentón dulce (opcional)\n\n" +
                "- el zumo de medio limón\n\n" +
                "- sal y pimienta\n\n")

        ingredientes.add("- 1 kilo de asiento o posta\n\n" +
                "- 3 cebollines\n\n" +
                "- 2 cdas de aceite\n\n" +
                "- 2 cdtas de Ajo en Polvo Gourmet\n\n" +
                "- 1  cdta de Jengibre en Polvo Gourmet\n\n" +
                "- 1 ají verde en rodajas (la cantidad puede variar según el gusto)\n\n" +
                "- 3/4 taza de Salsa de Soya Gourmet\n\n" +
                "- 1 cdas de maicena\n\n" +
                "- 1,5 cdas de vino blanco\n\n" +
                "- 3/4 cdta de azúcar")



        //ArrayList con preparaciones

        val preparacion = ArrayList<String>()

        preparacion.add("Preparación del Bizcocho Casero")
        preparacion.add("Preparación del Ají de gallina")
        preparacion.add("Preparación del Hummus tradicional")
        preparacion.add("Preparación de la Carne mongoliana")



        //ACCION DEL CLICK EN LOS ELEMENTOS DE LA ACTIVITY
        btn_opciones_usuario.setOnClickListener {
            val intent = Intent(this@HomeActivity, OpcionesUsuarioActivity::class.java)
            startActivity(intent)
        }

        btn_aniadir_receta.setOnClickListener {
            val intent = Intent(this@HomeActivity, AniadirRecetaActivity::class.java)
            startActivity(intent)
        }

        lv_recetas.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(this@HomeActivity, DetalleRecetaActivity::class.java)

                // pasar index y nombre de receta a detalle receta
                intent.putExtra("index_receta", position)
                intent.putExtra("nombre_receta", nombres_recetas[position])
                intent.putExtra("index_categoria", categoria_receta[position])
                intent.putExtra("ingredientes_receta", ingredientes[position])
                intent.putExtra("preparacion_receta", preparacion[position])

                startActivity(intent)
            }

        }

        sp_categorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                //obtener valor seleccionado del spinner
                val categoria = sp_categorias.selectedItem.toString()

                //aplicar filtro si el valor es distinto a categoria.
                if(categoria != "Seleccione un categoría"){
                    Toast.makeText(this@HomeActivity, "Filtro: \"${categoria}\"", Toast.LENGTH_SHORT).show()
                }

            }

            //funcion necesaria, sin esta se rompe el codigo
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Sin funcion por el momento
            }

        }

    }
}