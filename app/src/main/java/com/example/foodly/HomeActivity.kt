package com.example.foodly

import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodly.roomdatabase.DB
import com.example.foodly.roomdatabase.entity.Receta
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, DB.Db::class.java,"foodly-database").allowMainThreadQueries().build()

        //REFERENCIAS WIDGETS
        val btn_opciones_usuario = findViewById<Button>(R.id.btn_opcionesUsuarioHome)
        val btn_aniadir_receta = findViewById<Button>(R.id.btn_aniadirRecHome)
        val rv_recetas = findViewById<RecyclerView>(R.id.rv_recetasHome)
        val sp_categorias = findViewById<Spinner>(R.id.sp_categoriasHome)
        val fb_ubicacion = findViewById<FloatingActionButton>(R.id.fb_ubicacionHome)

        //obtener username del intent
        val username = intent.getStringExtra("username")


        //Asignar Categorias a Spinner
        val arrayAdapterSpinner: ArrayAdapter<*>
        val categorias = ArrayList<String>()



        //Insertar categorias al array
        categorias.add("Seleccione un categoría")
        categorias.add("vegetariano")
        categorias.add("vegano")
        categorias.add("carnes")
        categorias.add("postre")
        categorias.add("postre_vegano")

        arrayAdapterSpinner = ArrayAdapter(this@HomeActivity, android.R.layout.simple_spinner_dropdown_item
            , categorias)

        sp_categorias.adapter = arrayAdapterSpinner



        //RecyclerView
        rv_recetas.layoutManager = LinearLayoutManager(this)


        //var recetas: List<Receta>

        cargarRecyclerView(username, sp_categorias.selectedItem.toString())


        //ACCION DEL CLICK EN LOS ELEMENTOS DE LA ACTIVITY
        btn_opciones_usuario.setOnClickListener {
            val intent = Intent(this@HomeActivity, OpcionesUsuarioActivity::class.java)

            intent.putExtra("username", username)

            startActivity(intent)
        }

        btn_aniadir_receta.setOnClickListener {
            val intent = Intent(this@HomeActivity, AniadirRecetaActivity::class.java)

            intent.putExtra("username", username)

            startActivity(intent)
        }



        sp_categorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                //obtener valor seleccionado del spinner
                val categoria = sp_categorias.selectedItem.toString()

                //aplicar filtro si el valor es distinto a categoria.
                if(categoria != "Seleccione un categoría"){
                    cargarRecyclerView(username, categoria)
                    Toast.makeText(this@HomeActivity, "Filtro: \"${categoria}\"", Toast.LENGTH_SHORT).show()
                }else{
                    cargarRecyclerView(username, categoria)
                }

            }

            //funcion necesaria, sin esta se rompe el codigo
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Sin funcion por el momento
            }

        }

        fb_ubicacion.setOnClickListener {
            val intent = Intent(this@HomeActivity, MapsActivity::class.java)
            startActivity(intent)
        }

    }

    fun cargarRecyclerView(username: String?, categoria: String){

        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, DB.Db::class.java,"foodly-database").allowMainThreadQueries().build()

        val rv_recetas = findViewById<RecyclerView>(R.id.rv_recetasHome)

        var recetas: List<Receta>

        if(categoria.equals("Seleccione un categoría")){
            //obtener recetas de DB
            recetas = room.daoReceta().obtenerRecetasUsuario(username)
        }else{
            recetas = room.daoReceta().obtenerRecetasUsuarioCategoria(username, categoria)
        }

        //crear lista de recetas
        var items = mutableListOf<Item>()

        //crear elementos que contengan la ruta de las imagenes
        val carnes = R.drawable.carnes
        val vegetariano = R.drawable.vegetariano
        val vegano = R.drawable.vegano
        val postre = R.drawable.postre
        val postre_vegano = R.drawable.postre_vegano


        //agregar elementos de todas las categorias
        for(receta in recetas){
            var image: Int = 0

            when(receta.categoria){
                "carnes" -> image = carnes
                "vegetariano" -> image = vegetariano
                "vegano" -> image = vegano
                "postre" -> image = postre
                "postre_vegano" -> image = postre_vegano
            }

            items.add(Item(receta.id, receta.nombre, receta.categoria, image, receta.ingredientes, receta.preparacion))
        }


        //asignacion del adapater al recyclerview
        val adapter = CustomAdapter(items) { item ->
            val intent = Intent(this@HomeActivity, DetalleRecetaActivity::class.java)

            // pasar id y username a detalle Receta
            intent.putExtra("username", username)
            intent.putExtra("id", item.id)

            startActivity(intent)
        }

        rv_recetas.adapter = adapter

        //IMPLEMENTAMOS GESTOS
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {return false}

            //IMPLEMENTAMOS DECORADOR CON LA LIBRERIA RecyclerViewSwipeDecorator
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@HomeActivity, R.color.colorError))
                    .addSwipeLeftCornerRadius(1, 15f)
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_forever_24)
                    .addSwipeLeftLabel("Eliminar")
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@HomeActivity, R.color.colorPrimary))
                    .addSwipeRightCornerRadius(1, 15f)
                    .addSwipeRightActionIcon(R.drawable.baseline_archive_24)
                    .addSwipeRightLabel("Archivar")
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        //mostrar elemento eliminado
                        Toast.makeText(this@HomeActivity, "\"${items[position].titulo}\" eliminado", Toast.LENGTH_SHORT).show()

                        //remover item del adapter
                        adapter.removeItem(position)
                    }
                    ItemTouchHelper.RIGHT -> {
                        //mostrar elemento archivado
                        Toast.makeText(this@HomeActivity, "\"${items[position].titulo}\" archivado", Toast.LENGTH_SHORT).show()

                        //remover item del adapter
                        adapter.removeItem(position)
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rv_recetas)
    }
}