    package com.hfad.firebaselogin

    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import com.google.firebase.firestore.FirebaseFirestore

    class SignupActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_signup)

            findViewById<Button>(R.id.bBack).setOnClickListener(){
                startActivity(Intent(this,MainActivity::class.java))
            }

            findViewById<Button>(R.id.bRegister).setOnClickListener(){
                val output = findViewById<TextView>(R.id.Output)
                val password = findViewById<EditText>(R.id.Password).text.toString()
                val confirmPassword = findViewById<EditText>(R.id.PasswordConfirm).text.toString()
                val email = findViewById<EditText>(R.id.email).text.toString()
                val fullName = findViewById<EditText>(R.id.fullName).text.toString()


                val db= FirebaseFirestore.getInstance()
                val user:MutableMap<String, Any> = HashMap()

                if(password!=confirmPassword){
                    output.text = "Passwords are NOT the same"
                } else {
                    output.text = "Sign up successful!"
                    user["password"]=password
                    user["email"]=email
                    user["fullName"]=fullName

                    db.collection("UserAuth")
                        .add(user)
                        .addOnSuccessListener {
                            Log.d("dbfirebase","save: ${user}")
                        }
                        .addOnFailureListener{
                            Log.d("dbfirebase failed","${user}")
                        }

                }



            }

        }
    }