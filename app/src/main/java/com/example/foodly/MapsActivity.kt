package com.example.foodly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.foodly.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.CameraPosition

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //asignar coordenadas a variables
        val santiago = LatLng(-33.459229,-70.645348)
        val sede_ciisa = LatLng(-33.44859477298771, -70.66779185433957)

        // añadir marcador en sede ciisa
        mMap.addMarker(MarkerOptions().position(sede_ciisa).title("Marcador en Sede ciisa"))

        //ubicar camara en santiago
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago,10f))

        //mover camara a sede ciisa (republica)
        val cameraPosition = CameraPosition.Builder()
            .target(sede_ciisa) // Establece el centro del mapa en Ciisa Republica
            .zoom(17f) // Establece el zoom
            .build() // Crea una CameraPosition a partir del constructor


        //Establecer tiempo delay mover camara
        val delay:Long = 1500

        Handler(this.mainLooper).postDelayed({
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }, delay)


    }
}