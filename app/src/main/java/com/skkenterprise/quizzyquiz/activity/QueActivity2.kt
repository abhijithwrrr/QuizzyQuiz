package com.skkenterprise.quizzyquiz.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.skkenterprise.quizzyquiz.Constants
import com.skkenterprise.quizzyquiz.Questions
import com.skkenterprise.quizzyquiz.R

class QueActivity2 : AppCompatActivity(), View.OnClickListener {

    lateinit var progressBar: ProgressBar
    lateinit var txtProgress: TextView
    lateinit var txtQuesID: TextView
    lateinit var imgQuestion: ImageView
    lateinit var txtOptionOne: TextView
    lateinit var txtOptionTwo: TextView
    lateinit var txtOptionThree: TextView
    lateinit var txtOptionFour: TextView
    lateinit var btnSubmit: Button


    private var mCurrentPosition: Int = 1 // Default and the first question position
    private var mQuestionsList: ArrayList<Questions>? = null

    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0

    // TODO (STEP 3: Create a variable for getting the name from intent.)
    // START
    private var mUserName: String? = null
    // END

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {


        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
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

        // TODO (STEP 4: Get the NAME from intent and assign it the variable.)
        // START
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        // END

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        txtOptionOne.setOnClickListener(this)
        txtOptionTwo.setOnClickListener(this)
        txtOptionThree.setOnClickListener(this)
        txtOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.txtOptionOne -> {

                selectedOptionView(txtOptionOne, 1)
            }

            R.id.txtOptionTwo -> {

                selectedOptionView(txtOptionTwo, 2)
            }

            R.id.txtOptionThree -> {

                selectedOptionView(txtOptionThree, 3)
            }

            R.id.txtOptionFour -> {

                selectedOptionView(txtOptionFour, 4)
            }

            R.id.btnSubmit -> {

                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {

                            // TODO (STEP 5: Now remove the toast message and launch the result screen which we have created and also pass the user name and score details to it.)
                            // START
                            val intent =
                                Intent(this@QueActivity2, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            // END
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_bg)
                    } else {
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btnSubmit.text = "FINISH"
                    } else {
                        btnSubmit.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    /**
     * A function for setting the question to UI components.
     */
    private fun setQuestion() {

        val question =
            mQuestionsList!![mCurrentPosition - 1] // Getting the question from the list with the help of current position.

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit.text = "FINISH"
        } else {
            btnSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        txtProgress.text = "$mCurrentPosition" + "/" + progressBar.getMax()

        txtQuesID.text = question.question
        imgQuestion.setImageResource(question.image)
        txtOptionOne.text = question.optionOne
        txtOptionTwo.text = question.optionTwo
        txtOptionThree.text = question.optionThree
        txtOptionFour.text = question.optionFour
    }

    /**
     * A function to set the view of selected option view.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@QueActivity2,
            R.drawable.selected_option_border_bg
        )
    }

    /**
     * A function to set default options view when the new question is loaded or when the answer is reselected.
     */
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, txtOptionOne)
        options.add(1, txtOptionTwo)
        options.add(2, txtOptionThree)
        options.add(3, txtOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QueActivity2,
                R.drawable.default_option_border_bg
            )
        }
    }

    /**
     * A function for answer view which is used to highlight the answer is wrong or right.
     */
    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                txtOptionOne.background = ContextCompat.getDrawable(
                    this@QueActivity2,
                    drawableView
                )
            }
            2 -> {
                txtOptionTwo.background = ContextCompat.getDrawable(
                    this@QueActivity2,
                    drawableView
                )
            }
            3 -> {
                txtOptionThree.background = ContextCompat.getDrawable(
                    this@QueActivity2,
                    drawableView
                )
            }
            4 -> {
                txtOptionFour.background = ContextCompat.getDrawable(
                    this@QueActivity2,
                    drawableView
                )
            }
        }
    }
}