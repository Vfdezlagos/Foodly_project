package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.foodly.roomdatabase.DB


class DetalleRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_receta)

        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, DB.Db::class.java,"foodly-database").allowMainThreadQueries().build()

        //REFERENCIAR WIDGETS
        val btn_volver = findViewById<Button>(R.id.btn_volverDetalleRec)
        val btn_actualizar = findViewById<Button>(R.id.btn_actualizarDetalleRec)
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminarDetalleRec)
        val tv_nombre_receta = findViewById<TextView>(R.id.tv_nombreRecetaDetalle)
        val tv_ingredientes = findViewById<TextView>(R.id.tv_ingredientesDetalle)
        val tv_preparacion = findViewById<TextView>(R.id.tv_preparacionDetalle)
        val tv_categoria = findViewById<TextView>(R.id.tv_categoriaRecetaDetalleRec)


        //obtener id de receta y username
        val id = intent.getLongExtra("id", 0)
        val username = intent.getStringExtra("username")


        //obtener receta
        val receta = room.daoReceta().obtenerRecetaId(id)


        //capturar y setear campos desde home

        //nombre de la receta
        val nombre = receta.nombre
        tv_nombre_receta.setText(nombre)

        //categoria de la receta
        val categoria = receta.categoria
        tv_categoria.setText(categoria)

        //ingredientes de la receta
        val ingredientes = receta.ingredientes
        tv_ingredientes.setText(ingredientes)

        //preparacion de la receta
        val preparacion = receta.preparacion
        tv_preparacion.setText(preparacion)



        //ACCION EVENTO CLICK
        btn_actualizar.setOnClickListener {
            val intent =Intent(this@DetalleRecetaActivity, ActualizarRecetaActivity::class.java)

            //Pasar datos a actualizarRecetaActivity
            intent.putExtra("id", id)
            intent.putExtra("username", username)
            intent.putExtra("nombre", nombre)
            intent.putExtra("categoria", categoria)
            intent.putExtra("ingredientes", ingredientes)
            intent.putExtra("preparacion", preparacion)

            startActivity(intent)
        }

        btn_eliminar.setOnClickListener {
            val intent =Intent(this@DetalleRecetaActivity, EliminarRecetaActivity::class.java)

            //Pasar datos a eliminar receta activity
            intent.putExtra("id", id)
            intent.putExtra("username", username)

            startActivity(intent)
        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@DetalleRecetaActivity, HomeActivity::class.java)

            intent.putExtra("id", id)
            intent.putExtra("username", username)

            startActivity(intent)
        }
    }
}