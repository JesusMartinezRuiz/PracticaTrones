package com.example.trones

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.lang.RuntimeException

lateinit var button1:Button
lateinit var button2:Button
lateinit var config:Button



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1=findViewById(R.id.main_btn_ej1)
        button2=findViewById(R.id.main_btn_ej2)
        config=findViewById(R.id.main_btn_config)

        config.setOnClickListener {
            val actividad = Intent(applicationContext,CalculaConfig::class.java)
            startActivity (actividad)
        }


    }

    fun changeActivity(v:View){
        val idActivity = when(v.id){
            R.id.main_btn_ej1 -> MemoryMain::class.java
            R.id.main_btn_ej2 -> CalculaMain::class.java
            else->{
                throw RuntimeException("No se encontro la actividad")
            }
        }
        val intent=Intent(this,idActivity)
        startActivity(intent)
    }



}