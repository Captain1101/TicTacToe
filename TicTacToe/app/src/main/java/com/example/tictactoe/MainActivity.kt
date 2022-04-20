package com.example.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.tictactoe.databinding.ActivityMainBinding
import androidx.appcompat.app.AlertDialog


/*
=========================================================================

    Code inspired from https://github.com/codeWithCal/TicTacToeAndroid

=========================================================================
*/

class MainActivity : AppCompatActivity() {

    enum class Turn { CROSS, CIRCLE }
    private var firstTurn = Turn.CROSS
    private var nowTurn = Turn.CROSS
    private var pvc = false
    private var boardList = mutableListOf<Button>()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        begin()
    }
    private fun initBoard()
    {
        boardList.add(binding.button1)
        boardList.add(binding.button2)
        boardList.add(binding.button3)
        boardList.add(binding.button4)
        boardList.add(binding.button5)
        boardList.add(binding.button6)
        boardList.add(binding.button7)
        boardList.add(binding.button8)
        boardList.add(binding.button9)
    }

    private fun begin() {
        AlertDialog.Builder(this)
            .setTitle("Greetings, choose game mode")
            .setPositiveButton("PvP") { _,_ -> resetBoard() }
            .setNeutralButton("PvC") { _,_ -> pvcc() }
            .setCancelable(false)
            .show()
    }
    private fun pvcc() {pvc = true}

    fun aClick(view: View) {
        if (view !is Button)
            return
        addToBoard(view)
        if (pvc == true && !fullBoard()) {
            var count = 0
            for (button in boardList) {
                if (button.text == "") { count += 1 }
            }
            var count2 = (0..(count-1)).random()
            for (button in boardList) {
                if (button.text == "" && count2>0){
                    count2 -= 1
                    continue
                }
                if (button.text == "" && count2==0) {
                    addToBoard(button)
                    break
                }
            }
        }
        checkIfWin()
    }
    private fun checkIfWin() {
        if (victory("X")) {
            result("Cross Wins")
            resetBoard()
            return
        }
        else if(victory("O")){
            result ("Circle Wins")
            resetBoard()
            return
        }

        if(fullBoard()) {
            result("Draw")
        }
    }
    private fun victory(symbol: String): Boolean {
        if(match(binding.button1,symbol) && match(binding.button2,symbol) && match(binding.button3,symbol)) return true
        if(match(binding.button4,symbol) && match(binding.button5,symbol) && match(binding.button6,symbol)) return true
        if(match(binding.button7,symbol) && match(binding.button8,symbol) && match(binding.button9,symbol)) return true
        if(match(binding.button1,symbol) && match(binding.button4,symbol) && match(binding.button7,symbol)) return true
        if(match(binding.button2,symbol) && match(binding.button5,symbol) && match(binding.button8,symbol)) return true
        if(match(binding.button3,symbol) && match(binding.button6,symbol) && match(binding.button9,symbol)) return true
        if(match(binding.button1,symbol) && match(binding.button5,symbol) && match(binding.button9,symbol)) return true
        if(match(binding.button3,symbol) && match(binding.button5,symbol) && match(binding.button7,symbol)) return true
        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("Play Again")
            { _,_ -> resetBoard() }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for (button in boardList){
            button.text = ""
        }
        if(firstTurn == Turn.CIRCLE) firstTurn = Turn.CROSS
        else if(firstTurn == Turn.CROSS) firstTurn = Turn.CIRCLE
    }

    private fun fullBoard(): Boolean {
        for(button in boardList){
            if(button.text == "") return false
        }
        return true
    }

    private fun addToBoard(button: Button){
        if (button.text != "")
            return
        if (nowTurn == Turn.CIRCLE){
            button.setTextColor(Color.parseColor("#00ABFF"))
            button.text = "O"
            nowTurn = Turn.CROSS
        } else if (nowTurn == Turn.CROSS){
            button.setTextColor(Color.parseColor("#FF0000"))
            button.text = "X"
            nowTurn = Turn.CIRCLE
        }
        setMainLabel()
    }

    private fun setMainLabel() {
        var turnText = ""
        if (nowTurn == Turn.CROSS)
            turnText = "X"
        else if (nowTurn == Turn.CIRCLE)
            turnText = "O"
        binding.MainNotification.text = turnText
    }

}