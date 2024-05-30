package com.example.news

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.news.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        binding.loginintentbtn.setOnClickListener {
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.registerBtn.setOnClickListener {

            val name = binding.nameText.text.toString()
            val email = binding.emailText.text.toString()
            val newPassword = binding.passwordText.text.toString()
            val repeatPassword = binding.repeatpasswordEdTxt.text.toString()

            if( name.isEmpty() || email.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty() ){
                Toast.makeText(this, "Fill the full information", Toast.LENGTH_SHORT).show()
            }else if (newPassword != repeatPassword){
                Toast.makeText(this, "password is not same", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email,newPassword)
                    .addOnCompleteListener (this){ task ->
                        if (task.isSuccessful)
                        {
                            Toast.makeText(this, "Registration is succesful", Toast.LENGTH_SHORT).show()
                            val intent =Intent(this@RegisterActivity , MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                        {
                            Toast.makeText(this, "Registration failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}