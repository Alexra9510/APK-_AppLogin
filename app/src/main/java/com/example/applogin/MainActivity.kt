package com.example.applogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoToRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        etEmail = findViewById(R.id.et_email_login)
        etPassword = findViewById(R.id.et_password_login)
        btnLogin = findViewById(R.id.btn_login)
        btnGoToRegister = findViewById(R.id.btn_go_to_register)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.checkUser(email, password)) {
                Toast.makeText(this, "Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UsersActivity::class.java)
                startActivity(intent)
                // NO Cierres la actividad de inicio de sesión aquí
                // finish()
            } else {
                Toast.makeText(this, "Credenciales inválidas.", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}