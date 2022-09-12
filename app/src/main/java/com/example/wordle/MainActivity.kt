package com.example.wordle

import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun checkGuess(guess: String, answer: String, letterView: Array<TextView>) : String {
            var result = ""
            for (i in 0..7) {
                letterView[i].text = guess[i % 4].toString()
                if (i > 3) {
                    if (guess[i % 4] == answer[i % 4]) {
                        letterView[i].setTextColor(rgb(0, 255, 0))
                        result += "o"
                    } else if (guess[i % 4] in answer) {
                        letterView[i].setTextColor(rgb(255, 0, 0))
                        result += "+"
                    } else {
                        letterView[i].setTextColor(rgb(115, 115, 115))
                        result += "x"
                    }
                }
            }
            return result
        }

        fun showCorrect(answer: TextView, messageBox: TextView, button: Button){
            answer.visibility = View.VISIBLE
            messageBox.text = "You are correct"
            button.text = "RESET"
        }

        var attempt = 1
        //var result : String = ""

        // Fields at bottom of screen
        val button: Button = findViewById(R.id.buttonCheck)
        val getGuess : EditText= findViewById(R.id.inputGuess)
        val answer: TextView = findViewById(R.id.Answer)
        val messageBox: TextView = findViewById(R.id.messageBox)

        // get random 4 letter word and place it in "answer" text view
        var wordToGuess: String = FourLetterWordList.getRandomFourLetterWord()
        answer.text = wordToGuess

        // word text views for three guesses
        val textViews: Array<TextView> = arrayOf(
                findViewById(R.id.guess1), findViewById(R.id.guess1check),
                findViewById(R.id.guess2), findViewById(R.id.guess2check),
                findViewById(R.id.guess3), findViewById(R.id.guess3check))

        // letter text views for three guesses
        val guess1Letters : Array<TextView> = arrayOf(
                findViewById(R.id.g1l1), findViewById(R.id.g1l2),
                findViewById(R.id.g1l3), findViewById(R.id.g1l4),
                findViewById(R.id.g1l1c), findViewById(R.id.g1l2c),
                findViewById(R.id.g1l3c), findViewById(R.id.g1l4c))

        val guess2Letters : Array<TextView> = arrayOf(
                findViewById(R.id.g2l1), findViewById(R.id.g2l2),
                findViewById(R.id.g2l3), findViewById(R.id.g2l4),
                findViewById(R.id.g2l1c), findViewById(R.id.g2l2c),
                findViewById(R.id.g2l3c), findViewById(R.id.g2l4c))

        val guess3Letters : Array<TextView> = arrayOf(
                findViewById(R.id.g3l1), findViewById(R.id.g3l2),
                findViewById(R.id.g3l3), findViewById(R.id.g3l4),
                findViewById(R.id.g3l1c), findViewById(R.id.g3l2c),
                findViewById(R.id.g3l3c), findViewById(R.id.g3l4c))

        button.setOnClickListener {
            val guessString : String = getGuess.text.toString().uppercase()
            getGuess.text.clear()   //text = null

            if (attempt > 3) {
                messageBox.text = "you have 3 guesses remaining"
                attempt = 1
                answer.visibility = View.INVISIBLE
                wordToGuess = FourLetterWordList.getRandomFourLetterWord()
                answer.text = wordToGuess
                button.text = "CHECK"
                getGuess.visibility = View.VISIBLE

                for (view in textViews){
                    view.visibility = View.INVISIBLE
                }
                for (letter in guess1Letters){
                    letter.text = " "
                }
                for (letter in guess2Letters){
                    letter.text = " "
                }
                for (letter in guess3Letters){
                    letter.text = " "
                }
            } else if (guessString.length != 4) {
                messageBox.text = "invalid entry, try again"
            } else if (attempt == 1){
                textViews[0].visibility = View.VISIBLE
                textViews[1].visibility = View.VISIBLE

                if (checkGuess(guessString, wordToGuess, guess1Letters) == "oooo"){
                    showCorrect(answer, messageBox, button)
                    attempt = 4
                    getGuess.visibility = View.INVISIBLE
                } else {
                    messageBox.text = "You have 2 more guesses"
                    attempt += 1
                }
            } else if (attempt == 2) {
                textViews[2].visibility = View.VISIBLE
                textViews[3].visibility = View.VISIBLE

                if (checkGuess(guessString, wordToGuess, guess2Letters) == "oooo"){
                    showCorrect(answer, messageBox, button)
                    attempt = 4
                    getGuess.visibility = View.INVISIBLE
                } else {
                    messageBox.text = "You have 1 more guess"
                    attempt += 1
                }
            } else if (attempt == 3) {
                textViews[4].visibility = View.VISIBLE
                textViews[5].visibility = View.VISIBLE

                if (checkGuess(guessString, wordToGuess, guess3Letters) == "oooo"){
                    showCorrect(answer, messageBox, button)
                    attempt = 4
                } else {
                    messageBox.text = "You did not guess the word, the word was"
                    answer.visibility = View.VISIBLE
                    button.text = "RESET"
                    attempt += 1
                }
                getGuess.visibility = View.INVISIBLE
            } else {
                messageBox.text = "Error found"
            }
        }
    }
}