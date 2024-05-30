package com.example.news

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.news.databinding.ActivityUserProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUserr = auth.currentUser
        if (currentUserr == null) {
            showToast("Something went wrong")
        } else {
            showProfile(currentUserr)
        }

        binding.backbtn.setOnClickListener {
            finish()
        }

        binding.profilePhoto.setOnClickListener {
            val intentUpdateProfile = Intent(this, UpdatePofilePicture::class.java)
            startActivity(intentUpdateProfile)
        }

        loadProfileImage()

    }

    private fun loadProfileImage() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("userImages").child(userId)
            databaseReference.get().addOnSuccessListener { snapshot ->
                val url = snapshot.child("url").value?.toString()
                if (!url.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(url)
                        .into(binding.profilePhoto)
                } else {
                    showToast("No image URL found.")
                }
            }.addOnFailureListener { exception ->
                showToast(exception.message ?: "Failed to fetch image URL.")
            }
        } else {
            showToast("User not logged in.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.updatename, R.id.refreshbtn -> {
                showToast("Updated")
                showToast("Jai Shree Ram Jai Hanuman", Toast.LENGTH_LONG)
                true
            }
            R.id.updateemail, R.id.updatepassword, R.id.updateDob, R.id.updatePhonenumber -> {
                handleMenuSelection(item.itemId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleMenuSelection(itemId: Int) {
        showToast("Jai Shree Ram")
        when (itemId) {
            R.id.updatepassword -> {
                val intentForgetPW = Intent(this, ForgetPWActivity::class.java)
                startActivity(intentForgetPW)
            }
            // Add other cases if needed for different actions
        }
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    private fun showProfile(firebaseUser: FirebaseUser) {

        database= Firebase.database.reference
        val userid = auth.currentUser!!.uid
        database.child("users").child(userid).get().addOnSuccessListener {

        }
        /*val userId = firebaseUser.uid
        database = FirebaseDatabase.getInstance().getReference("registered user").child(userId)

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.child("name").getValue(String::class.java)
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val phoneNumber = dataSnapshot.child("phoneNumber").getValue(String::class.java)
                val dateOfBirth = dataSnapshot.child("dateOfBirth").getValue(String::class.java)
               // val profilePhotoUrl = dataSnapshot.child("profilePhotoUrl").getValue(String::class.java)

                binding.nameprofiletxt.text = name ?: "N/A"
                binding.emailuserprofile.text = email ?: "N/A"
                binding.dobuserprofile.text = dateOfBirth ?: "N/A"
                binding.phoneNumTxt.text = phoneNumber ?: "N/A"


            }
            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to load profile: ${error.message}")
            }
        })*/
    }
}