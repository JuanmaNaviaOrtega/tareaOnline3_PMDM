package com.example.tareaonline3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tareaonline3_ejercicio2.MainActivity3
import tareaOnline3.ejercicio1.MainActivity
import tareaOnline3.ejercicio2.MainActivity2

class MenuActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnPrimeraApp = findViewById<Button>(R.id.btnprimeraApp)
       val btnSegundaApp = findViewById<Button>(R.id.btnsegundaApp)
        val btnTerceraApp = findViewById<Button>(R.id.btnterceraApp)

        btnPrimeraApp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSegundaApp.setOnClickListener {
           val intent = Intent(this, MainActivity2::class.java)
           startActivity(intent)}

        btnTerceraApp.setOnClickListener {

           val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}