package com.nukkadshops.mark01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.intellij.lang.annotations.Language

val DATABASE_NAME="CredentialsDB"
val TABLE_NAME="Users"
val COL_ID="id"
val COL_USERNAME="username"
val COL_PASSWORD="password"
val TABLE_NAME_LANGUAGES="Languages"
val COL_LANG_ID="lang_id"
val COL_LANG_NAME="lang_name"

class DatabaseHelper (context:Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable="CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT)"
        val createUserTable = "CREATE TABLE " + TABLE_NAME_LANGUAGES + " (" +
                COL_LANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_LANG_NAME + " TEXT)"
        db?.execSQL(createTable)
        db?.execSQL(createUserTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LANGUAGES)
        onCreate(db)
    }

    fun addUser(user: Users): Boolean {
        val db=this.writableDatabase
        val contentValues= ContentValues()
        contentValues.put(COL_USERNAME, user.username)
        contentValues.put(COL_PASSWORD, user.password)
        val result=db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_USERNAME=? AND $COL_PASSWORD=?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun insertLanguages(username: String, languages: List<String>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            for (language in languages) {
                val contentValues = ContentValues()
                contentValues.put(COL_USERNAME, username)
                contentValues.put(COL_LANG_NAME, language)
                db.insert(TABLE_NAME_LANGUAGES, null, contentValues)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun getLanguagesForUser(username: String): List<String> {
        val db = this.readableDatabase
        val languages = mutableListOf<String>()
        val query = "SELECT $COL_LANG_NAME FROM $TABLE_NAME_LANGUAGES WHERE $COL_USERNAME=?"
        val cursor = db.rawQuery(query, arrayOf(username))
        if (cursor.moveToFirst()) {
            do {
                languages.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return languages
    }


}