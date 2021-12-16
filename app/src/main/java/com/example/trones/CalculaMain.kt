package com.example.trones

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class CalculaMain : AppCompatActivity() {



    lateinit var botones_numeros: List<Button>
    lateinit var limpiar: Button
    lateinit var limpiarUltimo: Button
    lateinit var igual: Button
    lateinit var respuesta: TextView
    lateinit var cuentaAtras: TextView
    lateinit var operacion_actual: TextView
    lateinit var cuenta_anterior: TextView
    lateinit var foto1: ImageView
    lateinit var foto2: ImageView
    lateinit var tvAciertos: TextView
    lateinit var tvfallos: TextView
    lateinit var ct: CountDownTimer
    lateinit var signos_check: MutableList<Boolean>
    lateinit var signos: List<String>


    var resultado=0
    var contador:Long=0
    var input:String=""
    var aciertos= 0
    var fallos = 0
    var min=0
    var max=0


    lateinit var SP: SharedPreferences
    val sp_calculatron = MainActivity.app_id+"_calculaMain"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcula_main)
    }

    override fun onStart() {
        super.onStart()

        respuesta=findViewById(R.id.tv_respuesta)
        cuenta_anterior=findViewById(R.id.tv_anterior)
        limpiar=findViewById(R.id.btn_c)
        limpiarUltimo=findViewById(R.id.btn_ce)
        igual=findViewById(R.id.btn_equals)
        cuentaAtras=findViewById(R.id.tv_timer)
        foto1=findViewById(R.id.iv_cronometro)
        foto2=findViewById(R.id.iv_cronometro2)
        operacion_actual=findViewById(R.id.tv_cuentaActual)
        tvAciertos=findViewById(R.id.tv_aciertos)
        tvfallos=findViewById(R.id.tv_fallos)


        SP = getSharedPreferences(sp_calculatron, 0)


        contador = SP.getInt(getString(R.string.sp_tiempo), resources.getInteger(R.integer.tiempoDef)).toLong()
        min =  SP.getInt(getString(R.string.sp_minimo), resources.getInteger(R.integer.numMinimoDef))
        max =  SP.getInt(getString(R.string.sp_maximo), resources.getInteger(R.integer.numMaximoDef))



        val signos_check_int = resources.getIntArray(R.array.cuentasDef)
        signos= resources.getStringArray(R.array.operadores).toList()
        signos_check=BooleanArray(resources.getIntArray(R.array.cuentasDef).size).toMutableList()

        signos.forEachIndexed { i, p ->
            signos_check[i] = SP.getBoolean(p, signos_check_int[i]==1)
        }


        resultado=generarCuenta(min, max)

        ct = object : CountDownTimer(contador * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                contador--
                cuentaAtras.text=contador.toString()
            }

            override fun onFinish() {
                contador--
                cuentaAtras.text=contador.toString()

                with(SP.edit()){
                    putInt("Aciertos", aciertos)
                    putInt("Fallos", fallos)

                    commit()
                }
                val intent_result= Intent(applicationContext, CalculaResultados::class.java)
                startActivity(intent_result)
            }

        }.start()

        botones_numeros = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6,
            R.id.btn_7, R.id.btn_8, R.id.btn_9
        ).map { findViewById(it) }



        limpiar.setOnClickListener{
            limpiarTodo()
        }
        limpiarUltimo.setOnClickListener{
            ultimoLimpiado(input)
        }

        igual.setOnClickListener{
            igual(resultado, input.toInt())
            cuenta_anterior.text=operacion_actual.text.toString() + " = " + input.toInt()
            resultado=generarCuenta(min, max)
            limpiarTodo()
            resultado=generarCuenta(min, max)
        }

        botones_numeros.forEach {
            it.setOnClickListener { b ->
                concatenar(it.text.toString())
                respuesta.text=input
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        ct.cancel()
        val actividad= Intent(applicationContext, MainActivity::class.java)
        startActivity(actividad)
    }


    fun generarCuenta(min:Int, max:Int): Int {

        var num1 =(min..max).random()
        var num2 =(min..max).random()
        var n1=num1
        var n2=num2
        var operacion = (1..3).random()
        var result=0

        if (num2>num1){
            num1=n2
            num2=n1
        }

        if (operacion == 1){
            operacion_actual.text=num1.toString() + " + "+ num2.toString()
            result=num1+num2
        }else if(operacion == 2){
            operacion_actual.text=num1.toString() + " - "+ num2.toString()
            result=num1-num2
        }else{
            operacion_actual.text=num1.toString() + " * "+ num2.toString()
            result=num1*num2
        }
        return result
    }

    fun igual(user_val:Int, cuenta_solu:Int){
        if (user_val==cuenta_solu){
            aciertos++

        }else{
            fallos++
        }
        tvAciertos.text="Aciertos: "+aciertos.toString()
        tvfallos.text="Fallos: "+fallos.toString()
    }

    fun concatenar(valor:String){
        input+=valor
    }

    fun limpiarTodo(){
        input=""
        respuesta.text=input
    }

    fun ultimoLimpiado(str:String){
        var longitud=input.length
        if (longitud!=0) {
            input = str.replace(str.substring(str.length - 1), "")
            respuesta.text = input
        }
    }

}