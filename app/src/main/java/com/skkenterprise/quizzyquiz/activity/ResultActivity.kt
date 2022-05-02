package com.skkenterprise.quizzyquiz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.skkenterprise.quizzyquiz.Constants
import com.skkenterprise.quizzyquiz.R


class ResultActivity : AppCompatActivity() {

    lateinit var tv_name: TextView
    lateinit var tv_score: TextView
    lateinit var btnFinish: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val username = intent.getStringExtra(Constants.USER_NAME)
        tv_name.text = username

        val totatQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswer = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        tv_score.text = "Your Score is $correctAnswer out of $totatQuestions"

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}