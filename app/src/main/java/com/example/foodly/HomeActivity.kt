package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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


        //ACCION DEL METODO CLICK
        btn_opciones_usuario.setOnClickListener {
            val intent = Intent(this@HomeActivity, OpcionesUsuarioActivity::class.java)
            startActivity(intent)
        }

        btn_aniadir_receta.setOnClickListener {
            val intent = Intent(this@HomeActivity, AniadirRecetaActivity::class.java)
            startActivity(intent)
        }

        lv_recetas.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent = Intent(this@HomeActivity, DetalleRecetaActivity::class.java)
                startActivity(intent)
            }

        }

        sp_categorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                //obtener valor seleccionado del spinner
                val categoria = sp_categorias.selectedItem.toString()

                //aplicar filtro si el valor es distinto a categoria.
                if(categoria != "Categoría"){
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