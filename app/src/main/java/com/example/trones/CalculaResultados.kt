package com.example.trones

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CalculaResultados : AppCompatActivity() {
    lateinit var aciertos: TextView
    lateinit var fallos: TextView
    lateinit var total_esta: TextView
    lateinit var tv_aciertos_totales: TextView
    lateinit var tv_fallos_totales: TextView
    lateinit var tv_porcentaje_total: TextView

    lateinit var SP: SharedPreferences
    val sp_calculatron = MainActivity.app_id+"_calculaMain"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcula_resultados)
    }


    override fun onStart() {
        super.onStart()

        SP = getSharedPreferences(sp_calculatron, 0)

        sumaResultados()

        aciertos=findViewById(R.id.tv_aciertos_result)
        fallos=findViewById(R.id.tv_fallos_result)
        total_esta=findViewById(R.id.tv_centajepartida)
        tv_aciertos_totales=findViewById(R.id.tv_aciertos_totales)
        tv_fallos_totales=findViewById(R.id.tv_fallos_totales)
        tv_porcentaje_total=findViewById(R.id.porecentaje_total)

        var total_cuentas = SP.getInt("Aciertos", 0)+SP.getInt("Fallos", 0)

        var porcentaje_esta=
            if(total_cuentas==0){
                100.00
            }else{
                (SP.getInt("Aciertos", 0)*100)/total_cuentas
            }



        var cuentas_totales= SP.getInt("Aciertos_totales", 0)+SP.getInt("Fallos_totales", 0)
        var porcentaje_total=
            if(cuentas_totales==0){
                100.00
            }else{
                (SP.getInt("Aciertos_totales", 0)*100)/cuentas_totales
            }






        aciertos.text="Aciertos: "+ SP.getInt("Aciertos", 0).toString()
        fallos.text="Fallos: " + SP.getInt("Fallos", 0).toString()
        total_esta.text=getString(R.string.porcentaje_una_partida,porcentaje_esta.toFloat())+"%"


        tv_aciertos_totales.text="Aciertos totales "+ SP.getInt("Aciertos_totales", 0).toString()
        tv_fallos_totales.text="Fallos totales "+ SP.getInt("Fallos_totales", 0).toString()
        tv_porcentaje_total.text=getString(R.string.porcentaje_total,porcentaje_total.toFloat())+"%"
    }
    override fun onBackPressed() {
        super.onBackPressed()
        sumaResultados()
        val actividad= Intent(applicationContext, MainActivity::class.java)
        startActivity(actividad)
    }

    fun sumaResultados(){

        var ciertos_totales=SP.getInt("Aciertos_totales", 0)
        var fallos_totales=SP.getInt("Fallos_totales", 0)

        with(SP.edit()){
            putInt("Aciertos_totales", ciertos_totales+SP.getInt("Aciertos", 0))
            putInt("Fallos_totales", fallos_totales+SP.getInt("Fallos", 0))

            commit()
        }
    }
}