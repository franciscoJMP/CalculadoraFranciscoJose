@file:Suppress("DEPRECATION")

package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.*;

class MainActivity : AppCompatActivity() {

    var nuevoOperador=true;
    var punto=false;
    var actiBin=false;
    var actiHexa=false;
    var actiDec=true;
    var cambioNegativoPositivo="+";
    var numeroAnterior="0";
    var operador="+";
    var resultado=0.0;
    var resultadoHorizontal=0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun numero(v: View){
        if(nuevoOperador){
            tx_numero.setText("")
            nuevoOperador=false;
        }
        var numero=tx_numero.text.toString();
        var botonPulsado=v as Button;
        when(botonPulsado.id){
            bt_uno.id->{numero+="1"}
            bt_dos.id->{numero+="2"}
            bt_tres.id->{numero+="3"}
            bt_cuatro.id->{numero+="4"}
            bt_cinco.id->{numero+="5"}
            bt_seis.id->{numero+="6"}
            bt_siete.id->{numero+="7"}
            bt_ocho.id->{numero+="8"}
            bt_nueve.id->{numero+="9"}
            bt_AC.id->{
                borrarTodo(bt_AC)
                numero="0"
            }
            bt_cero.id->{
                numero+="0"
                if(numero.length==0){
                    nuevoOperador=true;
                    numero="0";
                }
            }

            bt_NP.id->{
                if(cambioNegativoPositivo=="+"){
                    if(numero.length>0){
                        numero="-$numero"
                        cambioNegativoPositivo="-";
                    } else{
                        numero="0";
                        nuevoOperador=true;
                    }

                } else{
                    if(numero.length>0){
                        numero=numero.substring(1,numero.length);
                        cambioNegativoPositivo="+";
                    }else{
                        numero="0"
                        nuevoOperador=true;
                    }

                }

            }
            bt_punto.id->{
                if(!punto && numero.length>0){
                    numero+="."
                    punto=true;
                }else if(punto && numero.length>0){
                    numero="0";
                    nuevoOperador=true;
                }

            }
            bt_A.id->{numero+="a"}
            bt_B.id->{numero+="b"}
            bt_C.id->{numero+="c"}
            bt_D.id->{numero+="d"}
            bt_E.id->{numero+="e"}
            bt_F.id->{numero+="f"}


        }



        tx_numero.setText(numero);

    }
    fun operacion(v:View){
        var botonPulsado=v as Button;
        nuevoOperador=true;
        if(actiBin){
            var numero=tx_numero.text.toString().toLong();
            var ndec=convertBinaryToDecimal(numero)
            numeroAnterior=ndec.toString();

        }
        if(actiHexa){
            var decimal=hexadecimalADecimal(tx_numero.text.toString()).toString()
            numeroAnterior=decimal
        }

        if(actiDec){
            punto=false;
            cambioNegativoPositivo="+";
            numeroAnterior=tx_numero.text.toString();
        }



        when(botonPulsado.id){
            bt_mas.id->{operador="+"}
            bt_menos.id->{operador="-"}
            bt_multi.id->{operador="*"}
            bt_div.id->{operador="/"}
        }



    }
    fun borrarTodo(v:View){
        nuevoOperador=true
        punto=false
        cambioNegativoPositivo="+"
        numeroAnterior="0"
        operador="+"
        actiBin=false;
        actiHexa=false;
        actiDec=true;
    }
    fun mostrarResultado(v:View){
        try {
            var nuevoNumero=tx_numero.text.toString()

            if(actiBin){
                var numero=nuevoNumero.toLong();
                var ndec=convertBinaryToDecimal(numero)
                nuevoNumero=ndec.toString();

            }
            if(actiHexa){
                var decimal=hexadecimalADecimal(tx_numero.text.toString()).toString()
                nuevoNumero=decimal
            }
            when(operador){
                "+"->{resultado=numeroAnterior.toDouble()+nuevoNumero.toDouble()}
                "-"->{resultado=numeroAnterior.toDouble()-nuevoNumero.toDouble()}
                "*"->{resultado=numeroAnterior.toDouble()*nuevoNumero.toDouble()}
                "/"->{resultado=numeroAnterior.toDouble()/nuevoNumero.toDouble()}
            }
            if((!resultado.isInfinite() && !resultado.isNaN())){

                if(actiBin){
                    resultadoHorizontal=Math.floor(resultado).toInt();
                    var resultadoBinario=resultadoHorizontal.toString()
                    tx_numero.setText(Integer.toBinaryString(resultadoBinario.toInt()))
                }
                if(actiHexa){
                    resultadoHorizontal=Math.floor(resultado).toInt();

                    tx_numero.setText(Integer.toHexString(resultadoHorizontal).toString())
                }
                if(actiDec){
                    tx_numero.setText(resultado.toString())
                }

            } else{
                Toast.makeText(applicationContext, "No se puede dividir entre 0", Toast.LENGTH_SHORT).show()
                tx_numero.setText("0")
                resultado=0.0
                resultadoHorizontal=0
                numeroAnterior="0"
            }

            nuevoOperador=true
        } catch (e: Exception) {
        }
    }


    fun porcentaje(v:View){
        var numero=tx_numero.text.toString().toDouble()/100
        tx_numero.setText(numero.toString())
        nuevoOperador=true
    }

    fun borrarCaracter(v:View){
        var numero=tx_numero.text.toString();
        var resultado="";
        when(numero.length){
            0->{
                resultado="0"
                nuevoOperador=true;
            }
            1->{
                resultado="0"
                nuevoOperador=true;
            }
            else->{
                resultado=numero.substring(0,numero.length-1)
            }

        }
        tx_numero.setText(resultado);
    }

    fun convertirDecimal(view: View) {
        if(actiHexa){
            bt_A.setEnabled(false)
            bt_B.setEnabled(false)
            bt_C.setEnabled(false)
            bt_D.setEnabled(false)
            bt_E.setEnabled(false)
            bt_F.setEnabled(false)


            tx_numero.setText(hexadecimalADecimal(tx_numero.text.toString()).toString())

        }
        if(actiBin){
            var num=tx_numero.text.toString().toBigDecimal();
            tx_numero.setText(convertBinaryToDecimal(num.toLong()).toString())
        }

        bt_dos.setEnabled(true)
        bt_tres.setEnabled(true)
        bt_cuatro.setEnabled(true)
        bt_cinco.setEnabled(true)
        bt_seis.setEnabled(true)
        bt_siete.setEnabled(true)
        bt_ocho.setEnabled(true)
        bt_nueve.setEnabled(true)
        bt_porcent.setEnabled(true);

        bt_NP.setEnabled(true)
        actiDec=true
        actiHexa=false
        actiBin=false

    }

    fun convertirBinario(view: View) {

        if(!actiBin){
            bt_dos.setEnabled(false)
            bt_tres.setEnabled(false)
            bt_cuatro.setEnabled(false)
            bt_cinco.setEnabled(false)
            bt_seis.setEnabled(false)
            bt_siete.setEnabled(false)
            bt_ocho.setEnabled(false)
            bt_nueve.setEnabled(false)
            bt_A.setEnabled(false)
            bt_B.setEnabled(false)
            bt_C.setEnabled(false)
            bt_D.setEnabled(false)
            bt_E.setEnabled(false)
            bt_F.setEnabled(false)
            bt_NP.setEnabled(false)
            bt_porcent.setEnabled(false);
        }
        if(actiDec){
            var numero=tx_numero.text.toString().toDouble();
            var numeroRedondeado=Math.floor(numero).toInt()
            var binario=Integer.toBinaryString(numeroRedondeado)
            tx_numero.setText(binario.toString())
        }
        if(actiHexa){
            var decimal=hexadecimalADecimal(tx_numero.text.toString()).toString()
            var numero =decimal.toLong();
            tx_numero.setText(Integer.toBinaryString(numero.toInt()).toString())

        }

        actiDec=false
        actiHexa=false
        actiBin=true
    }
    fun convertirHexa(view: View) {
        if(!actiHexa){
            bt_A.setEnabled(true)
            bt_B.setEnabled(true)
            bt_C.setEnabled(true)
            bt_D.setEnabled(true)
            bt_E.setEnabled(true)
            bt_F.setEnabled(true)
            bt_NP.setEnabled(false)
            bt_porcent.setEnabled(false)

        }

        if(actiBin){
            bt_dos.setEnabled(true)
            bt_tres.setEnabled(true)
            bt_cuatro.setEnabled(true)
            bt_cinco.setEnabled(true)
            bt_seis.setEnabled(true)
            bt_siete.setEnabled(true)
            bt_ocho.setEnabled(true)
            bt_nueve.setEnabled(true)
            bt_A.setEnabled(true)
            bt_B.setEnabled(true)
            bt_C.setEnabled(true)
            bt_D.setEnabled(true)
            bt_F.setEnabled(true)

            var num=tx_numero.text.toString().toBigDecimal();
            var decimal=convertBinaryToDecimal(num.toLong()).toString();
            tx_numero.setText(Integer.toHexString(decimal.toInt()).toString())

        }
        if(actiDec){
            var num=tx_numero.text.toString().toInt();
            tx_numero.setText(Integer.toHexString(num).toString());
        }
        actiHexa=true
        actiDec=false
        actiBin=false




    }
    fun convertBinaryToDecimal(num: Long): Int {
        var num = num
        var decimalNumber = 0
        var i = 0
        var remainder: Long

        while (num.toInt() != 0) {
            remainder = num % 10
            num /= 10
            decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
            ++i
        }
        return decimalNumber
    }

    fun hexadecimalADecimal(hexadecimal: String): Long {
        var decimal: Long = 0
        var potencia = 0
        for (x in hexadecimal.length - 1 downTo 0) {
            val valor = caracterHexadecimalADecimal(hexadecimal[x])
            val elevado = Math.pow(16.0, potencia.toDouble()).toLong() * valor
            decimal += elevado
            potencia++
        }
        return decimal
    }

    fun caracterHexadecimalADecimal(caracter: Char): Int {
        return when (caracter) {
            'a' -> 10
            'b' -> 11
            'c' -> 12
            'd' -> 13
            'e' -> 14
            'f' -> 15
            else -> caracter.toString().toInt()
        }
    }

}