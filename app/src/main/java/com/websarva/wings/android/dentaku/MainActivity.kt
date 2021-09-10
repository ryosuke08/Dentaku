package com.websarva.wings.android.dentaku

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    //数値
    //javaでは、String value = ""; の意味
    private var value = StringBuilder()

    //数値2
    private var valueChanger = StringBuilder()

    //演算子（+とか-とか）
    private var operation = ""

    //初期値
    private val initial = "0"

//    //演算子が押されたらtrueになるフラグ
//    private var flagOperation = false

    //＝ボタンが押されたらtrueになるフラグ
    private var flagEqual = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btn_Zero -> numberAction(0)
            R.id.btn_One -> numberAction(1)
            R.id.btn_Two -> numberAction(2)
            R.id.btn_Three -> numberAction(3)
            R.id.btn_Four -> numberAction(4)
            R.id.btn_Five -> numberAction(5)
            R.id.btn_Six -> numberAction(6)
            R.id.btn_Seven -> numberAction(7)
            R.id.btn_Eight -> numberAction(8)
            R.id.btn_Nine -> numberAction(9)
            R.id.btn_AC -> clearAction()
            R.id.btn_plus_minus -> plusMinusAction()
            R.id.btn_Tasu -> operationAction("+")
            R.id.btn_Hiku -> operationAction("-")
            R.id.btn_Kakeru -> operationAction("×")
            R.id.btn_Waru -> operationAction("÷")
            R.id.btn_Equal -> keisanAction()
            R.id.btn_Nijyou -> nijyouAction()
            R.id.btn_Color -> colorAction()
        }
    }


    private fun numberAction(num: Int) {

        if (flagEqual) {
            //＝ボタンが押された後に、数字ボタンが押された時、
            //答えの後ろに数字が加えられないようにするため。
            valueChanger.clear()
            flagEqual = false
        }

        if (valueChanger.toString() == "0") {
            //「025」とかにならないようにするため。
            valueChanger.clear()
        }
        valueChanger.append(num)
        findViewById<TextView>(R.id.disp).text = valueChanger.toString()
    }

    private fun clearAction() {
        value.clear()
        valueChanger.clear()
        operation = ""
        flagEqual = false
        findViewById<TextView>(R.id.disp).text = initial
    }

    private fun plusMinusAction() {
        if (valueChanger.isNotEmpty()) {
            if (valueChanger.toString() != "0") {
                val a = valueChanger.toString().toDouble() * (-1)
                valueChanger.clear()
                if (a - a.toInt().toDouble() == 0.0) {
                    valueChanger.append(a.toInt())
                } else {
                    valueChanger.append(a)
                }
                findViewById<TextView>(R.id.disp).text = valueChanger.toString()
            }
        }
    }

    private fun operationAction(mark: String) {
        if (operation == "") {
            operation = mark
            value.append(valueChanger)
            valueChanger.clear()
        } else {
            var result = 0.0
            when (operation) {
                "+" -> result = value.toString().toDouble() + valueChanger.toString().toDouble()
                "-" -> result = value.toString().toDouble() - valueChanger.toString().toDouble()
                "×" -> result = value.toString().toDouble() * valueChanger.toString().toDouble()
                "÷" -> result = value.toString().toDouble() / valueChanger.toString().toDouble()
            }
            value.clear()
            if (result.isInfinite() || result.isNaN()) {
                findViewById<TextView>(R.id.disp).text = "0除算エラー"
                valueChanger.clear()
                operation = ""

            } else {
                if (result - result.toInt().toDouble() == 0.0) {
                    value.append(result.toInt())
                } else {
                    value.append(result)
                }
                findViewById<TextView>(R.id.disp).text = value.toString()
                valueChanger.clear()
                operation = mark
            }
        }
    }

    private fun keisanAction() {
        if (operation != "" || value.isNotEmpty() || valueChanger.isNotEmpty()) {
            //演算子がちゃんと押されていて、1つ目の値と2つ目の値がちゃんと入力されている時。

            flagEqual = true

            var result = 0.0
            when (operation) {
                "+" -> result = value.toString().toDouble() + valueChanger.toString().toDouble()
                "-" -> result = value.toString().toDouble() - valueChanger.toString().toDouble()
                "×" -> result = value.toString().toDouble() * valueChanger.toString().toDouble()
                "÷" -> result = value.toString().toDouble() / valueChanger.toString().toDouble()
            }

            value.clear()

            if (result.isInfinite() || result.isNaN()) {
                findViewById<TextView>(R.id.disp).text = "0除算エラー"

            } else {
                if (result - result.toInt().toDouble() == 0.0) {
                    value.append(result.toInt())
                } else {
                    value.append(result)
                }
                findViewById<TextView>(R.id.disp).text = value.toString()
                valueChanger.clear()
                valueChanger.append(value)
            }
            value.clear()
            operation = ""
        }
    }

    private fun nijyouAction() {

        if (operation != "" && valueChanger.isEmpty()) {
            //演算子が押されていて、かつ、数字のボタンが押されていない時は
            //二乗できないようにする。
        } else {
            val num = valueChanger.toString().toDouble()
            valueChanger.clear()
            if (num - num.toInt().toDouble() == 0.0) {
                valueChanger.append(num.toInt() * num.toInt())
            } else {
                valueChanger.append(num * num)
            }
            findViewById<TextView>(R.id.disp).text = valueChanger.toString()

        }

    }

    private var flagColor = false

    private fun colorAction() {
        if (flagColor) {
            findViewById<LinearLayout>(R.id.LL).setBackgroundColor(Color.WHITE)
            findViewById<TextView>(R.id.disp).setTextColor(Color.BLACK)
            flagColor = false
        } else {
            findViewById<LinearLayout>(R.id.LL).setBackgroundColor(Color.BLACK)
            findViewById<TextView>(R.id.disp).setTextColor(Color.WHITE)
            flagColor = true
        }
    }

}