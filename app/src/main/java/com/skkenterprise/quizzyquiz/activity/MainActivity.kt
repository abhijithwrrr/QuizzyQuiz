package com.skkenterprise.quizzyquiz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.skkenterprise.quizzyquiz.Constants
import com.skkenterprise.quizzyquiz.R

class MainActivity : AppCompatActivity() {

    lateinit var btnStart: Button
    lateinit var etName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //To hide the status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btnStart = findViewById(R.id.btnStart)
        etName = findViewById(R.id.etName)

        btnStart.setOnClickListener {
            if (etName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QueActivity2::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
                finish()

            }
        }


    }
}