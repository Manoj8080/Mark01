package com.nukkadshops.mark01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView
import android.app.AlertDialog
import android.content.Intent

class SecondActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adaptor
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var searchView: SearchView
    private var languageList = mutableListOf<Models>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)
        dbHelper = DatabaseHelper(this)

        val username = intent.getStringExtra("Username") ?: return
        val languages = dbHelper.getLanguagesForUser(username)
        languageList = languages
            .distinct() // remove duplicate names first
            .map { Models(it) } // then convert to Models
            .toMutableList()

        // Set up the adapter with item click listener
        adapter = Adaptor(this, languageList) { selectedModel ->
            // Handle item click
            showItemClickDialog(selectedModel)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.resetList()
                } else {
                    val filteredList = languageList.filter {
                        it.title.contains(newText, ignoreCase = true)
                    }
                    adapter.updateList(filteredList)
                }
                return true
            }
        })

        // Back press dialog
        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showLogoutDialog()
            }
        })
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("***LOGOUT***")
        builder.setMessage("Do You Want To LOGOUT?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showItemClickDialog(selectedModel: Models) {
        // Show a dialog or take any other action with the clicked item
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Item Selected")
        builder.setMessage("You selected: ${selectedModel.title}")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}
