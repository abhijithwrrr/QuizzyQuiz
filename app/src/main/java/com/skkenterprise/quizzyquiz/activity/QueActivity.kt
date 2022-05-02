package com.skkenterprise.quizzyquiz.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.skkenterprise.quizzyquiz.Constants
import com.skkenterprise.quizzyquiz.Questions
import com.skkenterprise.quizzyquiz.R

class QueActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var progressBar: ProgressBar
    lateinit var txtProgress: TextView
    lateinit var txtQuesID: TextView
    lateinit var imgQuestion: ImageView
    lateinit var txtOptionOne: TextView
    lateinit var txtOptionTwo: TextView
    lateinit var txtOptionThree: TextView
    lateinit var txtOptionFour: TextView
    lateinit var btnSubmit: Button

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Questions>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswer: Int = 0
    private var mUserName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_que)

        progressBar = findViewById(R.id.progressBar)
        txtProgress = findViewById(R.id.txtProgress)
        txtQuesID = findViewById(R.id.txtQuesID)
        imgQuestion = findViewById(R.id.imgQuestion)
        txtOptionOne = findViewById(R.id.txtOptionOne)
        txtOptionTwo = findViewById(R.id.txtOptionTwo)
        txtOptionThree = findViewById(R.id.txtOptionThree)
        txtOptionFour = findViewById(R.id.txtOptionFour)
        btnSubmit = findViewById(R.id.btnSubmit)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()


        setQuestion()

        txtOptionOne.setOnClickListener(this)
        txtOptionTwo.setOnClickListener(this)
        txtOptionThree.setOnClickListener(this)
        txtOptionFour.setOnClickListener(this)

        btnSubmit.setOnClickListener(this)


    }

    private fun setQuestion() {


        val question = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit.text = "FINISH"
        } else {
            btnSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        txtProgress.text = "$mCurrentPosition" + "/" + progressBar.max

        txtQuesID.text = question.question

        imgQuestion.setImageResource(question.image)
        txtOptionOne.text = question.optionOne
        txtOptionTwo.text = question.optionTwo
        txtOptionThree.text = question.optionThree
        txtOptionFour.text = question.optionFour

    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, txtOptionOne)
        options.add(1, txtOptionTwo)
        options.add(2, txtOptionThree)
        options.add(3, txtOptionFour)


        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txtOptionOne -> {
                selectedOptionsView(txtOptionOne, 1)
            }
            R.id.txtOptionTwo -> {
                selectedOptionsView(txtOptionTwo, 2)
            }
            R.id.txtOptionThree -> {
                selectedOptionsView(txtOptionThree, 3)
            }
            R.id.txtOptionFour -> {
                selectedOptionsView(txtOptionFour, 4)
            }
        }

    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                txtOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }

            2 -> {
                txtOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                txtOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                txtOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            R.id.btnSubmit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswer)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                        }
                    }
                } else {
                    val questions = mQuestionsList?.get(mCurrentPosition - 1)
                    if (questions!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_bg)
                    } else {
                        mCorrectAnswer++
                    }
                    answerView(questions.correctAnswer, R.drawable.correct_option_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btnSubmit.text = "FINISH"
                    } else {
                        btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    /* mSelectedOptionPosition = 0*/
                }
            }

        }
    }

    private fun selectedOptionsView(txt: TextView, selectedOptionsNum: Int) {

        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionsNum
        txt.setTextColor(Color.parseColor("#363A43"))
        txt.setTypeface(txt.typeface, Typeface.BOLD)
        txt.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

    }
}