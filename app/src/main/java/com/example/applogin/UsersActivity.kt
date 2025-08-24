package com.example.applogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class UsersActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var lvUsers: ListView
    private lateinit var tvTitle: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        dbHelper = DatabaseHelper(this)

        lvUsers = findViewById(R.id.lv_users)
        tvTitle = findViewById(R.id.tv_users_title)
        btnLogout = findViewById(R.id.btn_logout)

        val userList = dbHelper.getAllUsers()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList.map { "Nombre: ${it.first}\nEmail: ${it.second}" })
        lvUsers.adapter = adapter

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Esto limpia todas las actividades por encima de MainActivity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}