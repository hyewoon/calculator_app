package com.example.calculator

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //3가지의 (전역)변수만들기

    var isFirstInput = true //첫번째 입력한 값 인가를 확인 하는 변수
    var isOperatorClick = false //연산자가 처음에는 안 눌렸으니까
    var resultNum : Double = 0.0 //결과겂을  저장하는 변수
    var inputNum : Double = 0.0
    var operator = "＝"// 연산자를 저장하는 변수
    var lastOperator ="＋"

    //왜 operator 초기값이 + 인가 resultNum이 0 이기때문에 다음에 입력된 값이 일단 더해저야 계산가능
     //ex) 0 + 3 = 3 이 resultNum에 들어간다


    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun numberClick(view: View) {

        // var myText = findViewById<TextView>(view.id) //메모리공간에 저장되어 있다
        var tag = view.tag.toString() //변수 메모리에 저장되며 저장공간 낭비된다 짧으니까 그냥 가져온다


        if (isFirstInput) { //첫번째 입력한 값이 맞는지 확인 첫번째 입력한 값이면
            // binding.results.setTextColor(Color.parseColor("#000000"))
            //binding.results.setTextColor(Color.BLACK)
            binding.results.text = tag
            isFirstInput = false //이걸 안 지정해주면 isfirstInput은 계속 true만 타게 된다
            if(operator.equals("＝")) {
                binding.calculate.text = " "
                isOperatorClick = false
            }

        } else { // 첫번째 입력한 값이 아니면
            if(binding.results.text.toString() == "0"){
                Toast.makeText(this@MainActivity, "0은 안돼", Toast.LENGTH_SHORT).show()
                isFirstInput =true
            }else {
                binding.results.append(tag)
            }

        }
    }

    fun operatorButtonClick(view: View){
        isOperatorClick = true
        lastOperator = view.tag.toString()
        if(isFirstInput) { //첫값이면
            if(operator.equals("＝")){
                operator = view.tag.toString()
                resultNum = binding.results.text.toString().toDouble()
                binding.calculate.text = resultNum.toString() + operator + " "
            }else{
                operator = view.tag.toString()
                var getCalculate = binding.calculate.text.toString()
                var subString = getCalculate.substring(0, getCalculate.length-2)
                binding.calculate.text = subString
                binding.calculate.append(operator + " ")
            }
        }else { //첫 값이 아니면
            inputNum = binding.results.text.toString().toDouble()

            calculator(resultNum, inputNum, operator)

            binding.results.text = resultNum.toString()
            isFirstInput = true
            operator = view.tag.toString()
            binding.calculate.append("$inputNum$operator ")
        }
    }

    @SuppressLint("SetTextI18n")
    fun equalsButtonClick(view: View){
            if(isFirstInput){ //"=" 버튼이 두번 눌렸으면
                if(isOperatorClick) {
                    binding.calculate.text =
                        resultNum.toString() + " " + lastOperator + " " + inputNum.toString() + " " + "="
                    calculator(resultNum, inputNum, lastOperator)
                    binding.results.text = resultNum.toString()
                }
            }else{
                inputNum = binding.results.text.toString().toDouble()

                calculator(resultNum, inputNum, operator)
                binding.results.text =resultNum.toString()
                isFirstInput =true
                operator = view.tag.toString()
                binding.calculate.append("$inputNum$operator ")
            }
        }


    fun pointButtonClick(view: View){
        if(isFirstInput){
            binding.results.text = "0" + view.tag.toString()
            isFirstInput = false
         }else{
             if(binding.results.text.toString().contains(".")){
                 Toast.makeText(this, "이미 소숫점 있습니다.", Toast.LENGTH_SHORT).show()

             }else{
                 binding.results.append(view.tag.toString())
             }
        }
    }


    fun allClearButton(view: View){
        binding.results.text = "0"
        binding.calculate.text = ""
        resultNum = 0.0
        isFirstInput = true
        isOperatorClick = false
        operator= "＝"

    }

    fun clearEntryButtonClick(view: View){
        binding.results.text = "0"
        isFirstInput =true

    }

    fun backspaceButtonClick(view: View){
        if(!isFirstInput){
            var getResults = binding.results.text.toString()
            if(getResults.length> 1){
                var subString = getResults.substring(0, getResults.length - 1)
                binding.results.text = subString

            }else {
                binding.results.text = "0"
                isFirstInput = true
            }
        }

    }

    private fun calculator(resultNum : Double, inputNum : Double, operator : String ) : Double {
        when (operator) {
            "＝" -> this.resultNum = inputNum
            "＋" -> this.resultNum += inputNum
            "－" -> this.resultNum -= inputNum
            "×" -> this.resultNum *= inputNum
            "÷" -> this.resultNum /= inputNum
        }
        return resultNum
    }


    }




