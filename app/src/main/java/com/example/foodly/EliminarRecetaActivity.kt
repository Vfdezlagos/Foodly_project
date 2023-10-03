package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class EliminarRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_receta)

        //REFERENCIAR WIDGETS
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminarRec)
        val btn_volver = findViewById<Button>(R.id.btn_volverEliminarRec)
        val tv_nombre_receta = findViewById<TextView>(R.id.tv_nombreRecetaEliminarRec)



        //Obtener datos del intent
        val index_receta = intent.getIntExtra("index_receta", 0)
        val index_categoria = intent.getIntExtra("index_categoria", 0)
        val nombre_receta = intent.getStringExtra("nombre_receta")
        val ingredientes = intent.getStringExtra("ingredientes_receta")
        val preparacion = intent.getStringExtra("preparacion_receta")



        //setear nombre de la receta
        tv_nombre_receta.setText(nombre_receta)



        //ACCION EVENTO CLICK
        btn_eliminar.setOnClickListener {

            val nombre_rec = tv_nombre_receta.text.toString()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación de eliminacion")
            builder.setMessage("¿Seguro que deseas eliminar esta receta?")
            builder.setPositiveButton(android.R.string.ok){
                dialog, which ->

                //Accion a realizar si se preciona ok
                Toast.makeText(this, "Receta \"${nombre_rec}\" Eliminada", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@EliminarRecetaActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@EliminarRecetaActivity, DetalleRecetaActivity::class.java)

            //Pasar valores a detalle activity
            intent.putExtra("index_receta", index_receta)
            intent.putExtra("index_categoria", index_categoria)
            intent.putExtra("nombre_receta", nombre_receta)
            intent.putExtra("ingredientes_receta", ingredientes)
            intent.putExtra("preparacion_receta", preparacion)

            startActivity(intent)
        }
    }
}