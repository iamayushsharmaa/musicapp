package com.example.news
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.example.news.databinding.ActivityUpdatePofilePictureBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class UpdatePofilePicture : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePofilePictureBinding
    private var firebaseUser: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var uri: Uri

    private var uriImage: Uri? = null
    private lateinit var storagereff: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePofilePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storagereff = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser

        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                if (it != null) {
                    binding.updateProfileView.setImageURI(it)
                    uri = it
                }
            }
        )

        firebaseUser?.photoUrl?.let {
            uri = it
            Picasso.get().load(uri).into(binding.updateProfileView)
        }

        binding.updateBackBtn.setOnClickListener {
            finish()
        }

        binding.updateCameraBtn.setOnClickListener {
            galleryImage.launch("image/*")
        }

        binding.uploadbtn.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE
            storagereff.getReference("images").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { downloadUri ->
                            val uid = FirebaseAuth.getInstance().currentUser!!.uid

                            val mapImage = mapOf(
                                "url" to downloadUri.toString()
                            )
                            val databaseReference = FirebaseDatabase.getInstance().getReference("userImages")
                            databaseReference.child(uid).setValue(mapImage)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                                    binding.progressbar.visibility = View.GONE
                                    val intentuser = Intent(this,UserProfileActivity::class.java)
                                    startActivity(intentuser)
                                    finish()
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                                    binding.progressbar.visibility = View.GONE
                                }

                        }

                }

        }

    }
}