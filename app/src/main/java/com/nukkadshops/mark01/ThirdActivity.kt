package com.nukkadshops.mark01

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val languageText = findViewById<TextView>(R.id.name)
        val skillText = findViewById<TextView>(R.id.difficulty)
        val logoImage = findViewById<ImageView>(R.id.image)

        val response = intent.getStringExtra("apiResponse")

        if (response != null) {
            try {
                val jsonObject = JSONObject(response)
                val language = jsonObject.getString("language_name")
                val skill = jsonObject.getString("skill_level")
                val logoBase64 = jsonObject.getString("logo")

                languageText.text = language
                skillText.text = "Skill Level: $skill"

                val imageBytes = Base64.decode(logoBase64, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                logoImage.setImageBitmap(decodedImage)

            } catch (e: Exception) {
                e.printStackTrace()
                languageText.text = "Error parsing response"
            }
        } else {
            languageText.text = "No data received"
        }
    }
}
