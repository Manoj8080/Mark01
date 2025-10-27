package com.nukkadshops.mark01

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.nukkadshops.mark01.SecondActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var login: Button
    private var backPressedTime: Long = 0
    private lateinit var toast: Toast
    //private lateinit var dbHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val emailEt = findViewById<EditText>(R.id.email)
        val passwordEt = findViewById<EditText>(R.id.password)
        login = findViewById(R.id.loginclick)
        //dbHelper = DatabaseHelper(this)

        login.setOnClickListener {
            //val email = emailEt.text.toString().trim()
            //val password = passwordEt.text.toString().trim()
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)

//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

//            login.isEnabled = true
//            lifecycleScope.launch {
//                try {
//                    val response = ApiClient.api.login(LoginRequest(email, password))
//                    val bodyString = when {
//                        response.isSuccessful -> response.body()?.string()
//                        else -> response.errorBody()?.string()
//                    } ?: ""
//
//                    if (response.isSuccessful) {
//                        // ✅ Store credentials locally
//                        dbHelper.insertUser(email, password)
//                        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
//                            putExtra("api_response", bodyString)
//
//                            insertUser(username,password)
//                        }
//                        startActivity(intent)
//                    }else {
//                        Toast.makeText(this@MainActivity, "Login failed: $bodyString", Toast.LENGTH_SHORT).show()
//                        return@launch
//                    }
//
//                } catch (e: Exception) {
//                    Toast.makeText(this@MainActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
//                } finally {
//                    login.isEnabled = true
//                }
//            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linear)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (backPressedTime + 2000 > currentTime) {
                    toast.cancel()
                    finishAffinity()
                } else {
                    toast = Toast.makeText(
                        this@MainActivity,
                        "Press back again to exit",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
                backPressedTime = currentTime
            }
        })
    }
}
