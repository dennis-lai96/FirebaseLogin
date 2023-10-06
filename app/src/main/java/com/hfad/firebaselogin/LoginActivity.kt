package com.hfad.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.hfad.firebaselogin.R
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<Button>(R.id.bSignIn).setOnClickListener() {
            val email = findViewById<EditText>(R.id.loginEmail).text.toString()
            val password = findViewById<EditText>(R.id.SignInPassword).text.toString()

            authenticateUser(email, password)
        }

        findViewById<Button>(R.id.bHome).setOnClickListener(){
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    private fun authenticateUser(email: String, password: String) {
        val db = FirebaseFirestore.getInstance()
        val userCollection = db.collection("UserAuth")
        val output =findViewById<TextView>(R.id.alertMessage)

        userCollection.whereEqualTo("email", email)//Query Firestore to find a document with the matching email
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0]// User with the provided email exists
                    val storedPassword = document["password"] as String
                    if (password == storedPassword) {
                        output.text = "Hello CS481"

                    } else {
                        output.text= "Either your id or password is incorrect"
                    }
                } else {
                    output.text= "User not Found"
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur during the query
                Log.d("Firestore", "Error querying Firestore: $exception")

            }
    }

}
