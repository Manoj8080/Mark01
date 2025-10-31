package com.nukkadshops.mark01

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var login: Button
    private lateinit var username : EditText
    private lateinit var password : EditText
    private var backPressedTime: Long = 0
    private lateinit var toast: Toast
    private lateinit var dbHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.loginclick)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        dbHelper = DatabaseHelper(this)

        //api calling
        login.setOnClickListener {
            val username1 = username.text.toString().trim()
            val password1 = password.text.toString().trim()


            if(username1.isEmpty()){
                Toast.makeText(this,"***ENTER USERNAME***", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password1.isEmpty()){
                Toast.makeText(this,"***ENTER PASSWORD***", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(username1.isEmpty() || password1.isEmpty()){
                Toast.makeText(this,"***ENTER USERNAME AND PASSWORD***", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login.isEnabled = false

            lifecycleScope.launch {

                try{
                    val response = ApiClient.api.login(LoginRequest(username1,password1))
                    if (response.isSuccessful && response.body()?.message=="Login successful"){
                        val data = response.body()
                        dbHelper.addUser(Users(username1,password1))
                        data?.let {
                            val languageNames=data.languages.map{lang->lang.language_name}
                            dbHelper.insertLanguages(username1,languageNames)
                        }
                        Toast.makeText(this@MainActivity, "Login Successful",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, SecondActivity::class.java)
                        intent.putExtra("Username",username1)
                        intent.putExtra("userId", data?.userId)  // <--- userId
                        intent.putParcelableArrayListExtra("languages", ArrayList(data?.languages))
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity,"Login Failed", Toast.LENGTH_SHORT).show()
                        //Log.e("LOGIN_ERROR", "Response code: ${response.code()}, message: ${response.message()}")
                    }

                }catch (e: Exception){
                    Toast.makeText(this@MainActivity,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }finally {
                    login.isEnabled = true
                }

            }

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
