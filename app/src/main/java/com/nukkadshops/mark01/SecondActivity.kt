package com.nukkadshops.mark01

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class SecondActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adaptor
    private lateinit var itemList: MutableList<Models>

    private var userId: Int = 0
    private lateinit var languages: List<Language>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // ✅ Receive from MainActivity
        userId = intent.getIntExtra("userId", 0)
        languages = intent.getParcelableArrayListExtra("languages") ?: emptyList()

        // ✅ Create item list from API data
        itemList = languages.map { Models(it.language_name) }.toMutableList()

        recyclerView = findViewById(R.id.recyclerView)
        adapter = Adaptor(this, itemList) { selectedItem ->
            val selectedLanguage = languages.find { it.language_name == selectedItem.title }
            val languageId = selectedLanguage?.id ?: 0
            fetchLanguageData(userId, languageId)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                showLogoutDialog()
            }
        })
    }

    // ✅ Fetch second API data using Volley
    private fun fetchLanguageData(userId: Int, languageId: Int) {
        val url = "https://626be3e927e2.ngrok-free.app/api/languages/$userId/$languageId"

        val requestQueue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val intent = Intent(this, ThirdActivity::class.java)
                intent.putExtra("apiResponse", response)
                startActivity(intent)
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )

        requestQueue.add(stringRequest)
    }
    private fun showLogoutDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("**LOGOUT**")
        builder.setMessage("Do You Want To Logout ?")
        builder.setPositiveButton("yes"){ dialog, _->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            dialog.dismiss()
        }
        builder.setNegativeButton("no"){ dialog, _->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

}
