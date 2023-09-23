package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DetalleRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_receta)

        //REFERENCIAR WIDGETS
        val btn_volver = findViewById<Button>(R.id.btn_volverDetalleRec)
        val btn_actualizar = findViewById<Button>(R.id.btn_actualizarDetalleRec)
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminarDetalleRec)

        //ACCION EVENTO CLICK
        btn_actualizar.setOnClickListener {
            val intent =Intent(this@DetalleRecetaActivity, ActualizarRecetaActivity::class.java)
            startActivity(intent)
        }

        btn_eliminar.setOnClickListener {
            val intent =Intent(this@DetalleRecetaActivity, EliminarRecetaActivity::class.java)
            startActivity(intent)
        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@DetalleRecetaActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}