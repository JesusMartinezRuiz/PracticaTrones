package com.example.trones

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible

class MemoryMain : AppCompatActivity() {

    data class Carta(val imagen:Int)

    lateinit var tvResult:TextView
    lateinit var reintentar:Button
    lateinit var vida1:ImageView
    lateinit var vida2:ImageView
    lateinit var vida3:ImageView
    val context = this
    lateinit var ct: CountDownTimer
    lateinit var cards: List<ImageView>
    lateinit var carta1:ImageView
    lateinit var carta2:ImageView
    var parejas=0
    var carta1click=0
    var carta2click=0
    var vidas=3



    val imgcard = listOf(
        Carta( R.drawable.arceus),
        Carta( R.drawable.dialga),
        Carta( R.drawable.lugia),
        Carta(R.drawable.rayquaza),
        Carta( R.drawable.snorlax),
        Carta( R.drawable.zardex),
        Carta( R.drawable.arceus),
        Carta( R.drawable.dialga),
        Carta( R.drawable.lugia),
        Carta( R.drawable.rayquaza),
        Carta( R.drawable.snorlax),
        Carta( R.drawable.zardex),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_main)
        //if (regProfile.getDrawable().getConstantState() == .getDrawable( R.drawable.ivpic).getConstantState())


    }

    override fun onStart() {
        super.onStart()

        vida1=findViewById(R.id.iv_vida1_memory)
        vida2=findViewById(R.id.iv_vida2_memory)
        vida3=findViewById(R.id.iv_vida3_memory)
        reintentar=findViewById(R.id.btn_memoryIntentar)
        tvResult=findViewById(R.id.memory_result)

        var imgcards=imgcard.shuffled()

        cards= listOf(
            R.id.iv1_memory, R.id.iv2_memory, R.id.iv3_memory, R.id.iv4_memory, R.id.iv5_memory, R.id.iv6_memory, R.id.iv7_memory,
            R.id.iv8_memory, R.id.iv9_memory, R.id.iv10_memory, R.id.iv11_memory, R.id.iv12_memory
        ).map { findViewById(it) }

        reintentar.setOnClickListener{
            val actividad = Intent(applicationContext,MemoryMain::class.java)
            startActivity (actividad)
        }


        cards.forEach {
            it.setOnClickListener { b ->
                it.setImageResource(imgcards[cards.indexOf(b)].imagen)
                if (carta1click==0){
                    carta1=it
                    carta1click++
                }else{
                    carta2=it
                    carta2click++
                }

                if (carta2click!=0){
                    if (carta2.getDrawable().getConstantState() == carta1.getDrawable().getConstantState()){
                        parejas++
                        resetclick()
                        if (parejas==6){
                            reintentar.isVisible=true
                            reintentar.text="HENORABUENA! PULSA PARA VOLVER A INTENTAR"
                        }

                    }else{
                        vidas--
                        var contador: Long = 2

                        cards.forEach {
                            it.isClickable=false
                        }

                        ct = object : CountDownTimer(contador * 1000, 1000) {

                            override fun onTick(millisUntilFinished: Long) {
                                contador--
                            }

                            override fun onFinish() {
                               carta1.setImageResource(R.drawable.cardback)
                               carta2.setImageResource(R.drawable.cardback)
                                refreshVidas()
                                resetclick()
                                cards.forEach {
                                    it.isClickable=true
                                }

                                if (vidas==0) {
                                    reintentar.isVisible = true
                                    reintentar.text="HAS PERDIDO :( PULSA PARA REINTENTARLO!"
                                }

                            }
                        }.start()
                    }
                }


            }
        }


    }

    fun resetclick() {
        carta1click=0
        carta2click=0
    }

    fun refreshVidas() {
        if (vidas==2){
            vida3.isVisible=false
        }else if(vidas==1){
            vida2.isVisible=false
        }else if (vidas==0){
            vida1.isVisible=false
        }
    }

}
