package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class DetalleRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_receta)

        //REFERENCIAR WIDGETS
        val btn_volver = findViewById<Button>(R.id.btn_volverDetalleRec)
        val btn_actualizar = findViewById<Button>(R.id.btn_actualizarDetalleRec)
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminarDetalleRec)
        val tv_nombre_receta = findViewById<TextView>(R.id.tv_nombreRecetaDetalle)
        val tv_ingredientes = findViewById<TextView>(R.id.tv_ingredientesDetalle)
        val tv_preparacion = findViewById<TextView>(R.id.tv_preparacionDetalle)
        val tv_categoria = findViewById<TextView>(R.id.tv_categoriaRecetaDetalleRec)



        //Array list categorias
        val categorias = ArrayList<String>()

        //Insertar categorias al array
        categorias.add("Seleccione un categoría")
        categorias.add("Vegetariano")
        categorias.add("Vegano")
        categorias.add("Carnes")
        categorias.add("Postre")
        categorias.add("Postre Vegano")



        //capturar y setear campos desde home

        //Obtener index de la receta
        val index_receta = intent.getIntExtra("index_receta", 0)

        //nombre de la receta
        val nombre_receta = intent.getStringExtra("nombre_receta")
        tv_nombre_receta.setText(nombre_receta)

        //categoria de la receta
        val index_categoria = intent.getIntExtra("index_categoria", 0)
        tv_categoria.setText(categorias[index_categoria])

        //ingredientes de la receta
        val ingredientes_receta = intent.getStringExtra("ingredientes_receta")
        tv_ingredientes.setText(ingredientes_receta)

        //preparacion de la receta
        val preparacion_receta = intent.getStringExtra("preparacion_receta")
        tv_preparacion.setText(preparacion_receta)



        //ACCION EVENTO CLICK
        btn_actualizar.setOnClickListener {
            val intent =Intent(this@DetalleRecetaActivity, ActualizarRecetaActivity::class.java)

            //Pasar datos a actualizarRecetaActivity
            intent.putExtra("index_receta", index_receta)
            intent.putExtra("nombre_receta", nombre_receta)
            intent.putExtra("index_categoria", index_categoria)
            intent.putExtra("ingredientes_receta", ingredientes_receta)
            intent.putExtra("preparacion_receta", preparacion_receta)

            startActivity(intent)
        }

        btn_eliminar.setOnClickListener {
            val intent =Intent(this@DetalleRecetaActivity, EliminarRecetaActivity::class.java)

            //Pasar datos a eliminar receta activity
            intent.putExtra("index_receta", index_receta)
            intent.putExtra("nombre_receta", nombre_receta)
            intent.putExtra("index_categoria", index_categoria)
            intent.putExtra("ingredientes_receta", ingredientes_receta)
            intent.putExtra("preparacion_receta", preparacion_receta)

            startActivity(intent)
        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@DetalleRecetaActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}