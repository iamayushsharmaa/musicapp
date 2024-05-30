package com.example.news

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.news.databinding.ActivityForgetPwactivityBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ForgetPWActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgetPwactivityBinding
    private  var TAG = "ForgetPWActivity"
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityForgetPwactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_forget_pwactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.checkemailbtn.setOnClickListener {
            val email = binding.emailcheckedttxt.text.toString()
            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "enter the email ", Toast.LENGTH_SHORT).show()
                binding.emailcheckedttxt.error = "email is required"
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "enter the valid email ", Toast.LENGTH_SHORT).show()
                binding.emailcheckedttxt.error = "email is not valid"
            }else {
                resetPassword(email)

            }

        }


    }

    private fun resetPassword(email: String) {
        auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            task->
            if (task.isSuccessful) {
                Toast.makeText(
                    this,
                    "Please check your email for reset password",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }else {
                try {
                    throw task.exception ?: Exception("Unknown error occurred")

                }catch (e: FirebaseAuthInvalidUserException){

                    binding.emailcheckedttxt.error = "User does not exist or no longer valid. Please register agian"
                }catch (e : Exception){
                    Log.d(TAG, e.message!!)
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}


