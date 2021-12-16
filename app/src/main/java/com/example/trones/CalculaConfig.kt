package com.example.trones

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class CalculaConfig : AppCompatActivity() {
    lateinit var tiempo_secs: EditText
    lateinit var num_min: EditText
    lateinit var num_max: EditText
    lateinit var spi_animacion: Spinner
    lateinit var guardar: Button


    lateinit var operadores_def:MutableList<Boolean>
    lateinit var signo:List<String>

    var tiempo_def=0
    var minimo_def=0
    var maximo_def=10
    var anima_def=0
    lateinit var SP: SharedPreferences
    val sp_timer_name = MainActivity.app_id+"_calculatron"


    var animaciones = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcula_config)


        tiempo_secs=findViewById(R.id.config_timer)
        num_max=findViewById(R.id.config_maximo)
        num_min=findViewById(R.id.config_minimo)
        spi_animacion=findViewById(R.id.spi_animacion)
        val cbs = listOf(R.id.checksuma, R.id.checkresta, R.id.checkmul)


        SP= getSharedPreferences(sp_timer_name, 0)

        tiempo_def=SP.getInt(
            getString(R.string.sp_tiempo),
            resources.getInteger(R.integer.tiempoDef)
        )

        minimo_def=SP.getInt(
            getString(R.string.sp_minimo),
            resources.getInteger(R.integer.numMinimoDef)
        )

        maximo_def=SP.getInt(
            getString(R.string.sp_maximo),
            resources.getInteger(R.integer.numMaximoDef)
        )

        anima_def=SP.getInt(
            getString(R.string.sp_animacion)
            , resources.getInteger(R.integer.selectedAnima)
        )

        signo = resources.getStringArray(R.array.operadores).toList()
        val defChecked = resources.getIntArray(R.array.cuentasDef)

        operadores_def = BooleanArray(signo.size).toMutableList()


        signo.forEachIndexed { i,e->

            operadores_def[i]= SP.getBoolean(e, defChecked[i]==1)

        }



    }

    override fun onStart() {
        super.onStart()
        guardar=findViewById(R.id.config_btn_guardar)


        tiempo_secs.setText(tiempo_def.toString())
        num_max.setText(maximo_def.toString())
        num_min.setText(minimo_def.toString())


        guardar.setOnClickListener{


            if (tiempo_secs.text.toString().toInt()==0){

                Toast.makeText(this, "El tiempo no puede ser 0",
                Toast.LENGTH_SHORT).show()

            }else if (num_min.text.toString().toInt()>num_max.text.toString().toInt()){

                Toast.makeText(this, "El valor minimo tiene que ser manor que el maximo",
                Toast.LENGTH_SHORT).show()

            }


            with(SP.edit()){

                putInt(
                    getString(R.string.sp_tiempo),
                    tiempo_secs.text.toString().toInt()
                )

                putInt(
                    getString(R.string.sp_minimo),
                    num_min.text.toString().toInt()
                )

                putInt(
                    getString(R.string.sp_maximo),
                    num_max.text.toString().toInt()
                )

                putInt(
                    getString(R.string.sp_animacion)
                    , spi_animacion.selectedItemPosition
                )

                commit()
            }

            val actividad= Intent(applicationContext, MainActivity::class.java)
            startActivity(actividad)
        }


        val adap =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                animaciones.map{it}
            )
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spi_animacion.adapter = adap
    }




}