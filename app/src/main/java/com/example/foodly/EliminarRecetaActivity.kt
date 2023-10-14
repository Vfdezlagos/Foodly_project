package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.foodly.roomdatabase.DB
import kotlinx.coroutines.launch

class EliminarRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_receta)

        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, DB.Db::class.java,"foodly-database").allowMainThreadQueries().build()

        //REFERENCIAR WIDGETS
        val btn_eliminar = findViewById<Button>(R.id.btn_eliminarRec)
        val btn_volver = findViewById<Button>(R.id.btn_volverEliminarRec)
        val tv_nombre_receta = findViewById<TextView>(R.id.tv_nombreRecetaEliminarRec)



        //obtener id de receta y username
        val id = intent.getLongExtra("id", 0)
        val username = intent.getStringExtra("username")


        //obtener receta
        val receta = room.daoReceta().obtenerRecetaId(id)



        //setear nombre de la receta
        tv_nombre_receta.setText(receta.nombre)



        //ACCION EVENTO CLICK
        btn_eliminar.setOnClickListener {

            val nombre_rec = tv_nombre_receta.text.toString()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación de eliminacion")
            builder.setMessage("¿Seguro que deseas eliminar esta receta?")
            builder.setPositiveButton(android.R.string.ok){
                dialog, which ->

                //Accion a realizar si se preciona ok

                lifecycleScope.launch {
                    room.daoReceta().eliminarReceta(id)

                    Toast.makeText(this@EliminarRecetaActivity, "Receta \"${nombre_rec}\" Eliminada", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@EliminarRecetaActivity, HomeActivity::class.java)

                    //Pasar valores a detalle activity
                    intent.putExtra("id", id)
                    intent.putExtra("username", username)

                    startActivity(intent)
                }

            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }

        btn_volver.setOnClickListener {
            val intent = Intent(this@EliminarRecetaActivity, DetalleRecetaActivity::class.java)

            //Pasar valores a detalle activity
            intent.putExtra("id", id)
            intent.putExtra("username", username)


            startActivity(intent)
        }
    }
}