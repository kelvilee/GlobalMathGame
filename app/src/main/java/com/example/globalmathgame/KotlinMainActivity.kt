package com.example.globalmathgame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import java.util.Random

class KotlinMainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var txtQuestion: TextView
    private lateinit var txtCorrect: TextView
    private lateinit var txtCorrectStatus: TextView
    private lateinit var btnAns1: Button
    private lateinit var btnAns2: Button
    private lateinit var btnAns3: Button
    private lateinit var btnReset: Button
    private var answer: Int = 0
    private var qCount: Int = 0
    private var correct: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_main)

        txtQuestion = findViewById(R.id.txtQuestion)
        txtCorrect = findViewById(R.id.txtCorrect)
        txtCorrectStatus = findViewById(R.id.txtCorrectStatus)
        btnAns1 = findViewById(R.id.btnAns1)
        btnAns2 = findViewById(R.id.btnAns2)
        btnAns3 = findViewById(R.id.btnAns3)
        btnReset = findViewById(R.id.btnReset)
        btnAns1.setOnClickListener(this)
        btnAns2.setOnClickListener(this)
        btnAns3.setOnClickListener(this)
        btnReset.setOnClickListener(this)

        answer = generateQuestion() // initial question
        val strAns = answer.toString()
        generateAnswers(strAns)
        txtCorrect.text = correct.toString()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAns1 -> if (answer == Integer.parseInt(btnAns1.text.toString())) {
                ++correct
                txtCorrectStatus.setText(R.string.correct)
            } else {
                txtCorrectStatus.setText(R.string.wrong)
            }
            R.id.btnAns2 -> if (answer == Integer.parseInt(btnAns2.text.toString())) {
                ++correct
                txtCorrectStatus.setText(R.string.correct)
            } else {
                txtCorrectStatus.setText(R.string.wrong)
            }
            R.id.btnAns3 -> if (answer == Integer.parseInt(btnAns3.text.toString())) {
                ++correct
                txtCorrectStatus.setText(R.string.correct)
            } else {
                txtCorrectStatus.setText(R.string.wrong)
            }
            R.id.btnReset -> {
                txtCorrectStatus.text = ""
                qCount = -1
                correct = 0
            }
        }
        ++qCount
        if (qCount == 5) {
            // android notification
            txtCorrect.text = correct.toString()
            ++qCount
            val intent = Intent(this, DelayedMessageService::class.java)
            intent.putExtra(DelayedMessageService.EXTRA_MESSAGE, "Your score is $correct/5")
            startService(intent)
        } else if (qCount > 5) {
            // don't do anything past 5Q
        } else {
            // next set of questions
            txtCorrect.text = correct.toString()
            answer = generateQuestion() // initial question
            val strAns = answer.toString()
            generateAnswers(strAns)
        }
    }

    private fun generateQuestion(): Int {
        val rand = Random()

        val randOp = rand.nextInt(4)
        val answer: Int
        var question = ""

        var randInt1 = rand.nextInt(101)
        var randInt2 = rand.nextInt(101)
        if (randOp == 0) { // do addition
            question = "$randInt1 + $randInt2"
            answer = randInt1 + randInt2
        } else if (randOp == 1) { // do multiplication
            question = "$randInt1 X $randInt2"
            answer = randInt1 * randInt2
        } else if (randOp == 2) { // do subtraction
            while (randInt1 - randInt2 < 0) {
                randInt1 = rand.nextInt(101)
                randInt2 = rand.nextInt(101)
            }
            question = "$randInt1 - $randInt2"
            answer = randInt1 - randInt2
        } else { // do division
            while (randInt2 == 0 || randInt1 % randInt2 != 0) {
                randInt1 = rand.nextInt(101)
                randInt2 = rand.nextInt(101)
            }
            question = "$randInt1 / $randInt2"
            answer = randInt1 / randInt2
        }
        txtQuestion.text = question
        return answer
    }

    private fun generateAnswers(strAns: String) {
        // setting button answers
        val rand = Random()
        val randAnsPos = rand.nextInt(3)
        if (randAnsPos == 0) {
            btnAns1.text = strAns
            btnAns2.text = rand.nextInt(5000).toString()
            btnAns3.text = rand.nextInt(5000).toString()
        } else if (randAnsPos == 1) {
            btnAns1.text = rand.nextInt(5000).toString()
            btnAns2.text = strAns
            btnAns3.text = rand.nextInt(5000).toString()
        } else {
            btnAns1.text = rand.nextInt(5000).toString()
            btnAns2.text = rand.nextInt(5000).toString()
            btnAns3.text = strAns
        }
    }
}
