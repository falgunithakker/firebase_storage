package com.example.firebase_storage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firebase_storage.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var imagereference=Firebase.storage.reference
    private var currentfile:Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imge1.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                imageluncher.launch(it)
            }
        }
        binding.btnupload.setOnClickListener{
              uploadimage("1")
        }
    }
    private val imageluncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result->
        if(result.resultCode== RESULT_OK)
        {
            result?.data?.data?.let {
                currentfile=it
                binding.imge1.setImageURI(it)

            }
        }
        else
        {
            Toast.makeText(this,"canceled",Toast.LENGTH_LONG).show()
        }
    }
    private fun uploadimage(filename:String)
    {
        try {

            currentfile?.let {
                imagereference.child("images/$filename").putFile(it).addOnSuccessListener{
                    Toast.makeText(this,"upload successfully",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"error in upload",Toast.LENGTH_SHORT).show()
                }
            }

        }catch (e:Exception)
        {
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }
    }
}