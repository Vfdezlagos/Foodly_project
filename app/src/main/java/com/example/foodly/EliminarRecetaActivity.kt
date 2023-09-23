package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class EliminarRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_receta)

        //REFERENCIAR WIDGETS
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminarRec)
        val btn_volver = findViewById<Button>(R.id.btn_volverEliminarRec)
        val nombre_receta = findViewById<TextView>(R.id.tv_nombreRecetaEliminarRec)

        //ACCION EVENTO CLICK
        btn_eliminar.setOnClickListener {

            val nombre_rec = nombre_receta.text.toString()

            Toast.makeText(this, "Receta \"${nombre_rec}\" Eliminada", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@EliminarRecetaActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@EliminarRecetaActivity, DetalleRecetaActivity::class.java)
            startActivity(intent)
        }
    }
}