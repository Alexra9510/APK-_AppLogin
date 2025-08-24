package com.example.applogin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Inserta un nuevo usuario en la base de datos
    fun addUser(nombre: String, email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }
        val success = db.insert(TABLE_USERS, null, values)
        db.close()
        // Retorna true si la inserción fue exitosa, false si falló (ej. email duplicado)
        return success != -1L
    }

    // Verifica si un usuario existe con el email y contraseña dados
    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?", arrayOf(email, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // Obtiene una lista de todos los usuarios registrados
    fun getAllUsers(): List<Pair<String, String>> {
        val userList = mutableListOf<Pair<String, String>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_NOMBRE, $COLUMN_EMAIL FROM $TABLE_USERS", null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                userList.add(Pair(nombre, email))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }
}