package com.example.applogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etNombre: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        etNombre = findViewById(R.id.et_nombre_register)
        etEmail = findViewById(R.id.et_email_register)
        etPassword = findViewById(R.id.et_password_register)
        btnRegister = findViewById(R.id.btn_register)
        btnBack = findViewById(R.id.btn_back_to_login)

        btnRegister.setOnClickListener {
            val nombre = etNombre.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isSuccess = dbHelper.addUser(nombre, email, password)
            if (isSuccess) {
                Toast.makeText(this, "Usuario registrado exitosamente!", Toast.LENGTH_SHORT).show()
                finish() // Vuelve a la actividad anterior (MainActivity)
            } else {
                Toast.makeText(this, "Error: El email ya existe.", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish() // Vuelve a la actividad anterior
        }
    }
}