package com.example.backend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.backend.databinding.ActivityLoginBinding
import com.example.backend.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLogin()
    }

    private fun showLogin() {
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.btnLogin.setOnClickListener {
            val username = loginBinding.etUsername.text.toString()
            val password = loginBinding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por Favor Llena Todos los Campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(username, password)
            RetrofitClient.getClient().create(ApiService::class.java)
                .login(user).enqueue(object : Callback<ResponseMessage> {
                    override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                        val msg = if (response.isSuccessful) {
                            response.body()?.message ?: "Éxito"
                        } else {
                            "Usuaio y/o Contraseña Incorrectos. Intentalo de Nuevo."
                        }
                        loginBinding.tvStatus.text = msg
                    }
                    override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                        loginBinding.tvStatus.text = "Problemas de Conexión al Servidor.\nError: ${t.message}"
                    }
                })
        }

        loginBinding.tvGoToRegister.setOnClickListener {
            showRegister()
        }
    }

    private fun showRegister() {
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.btnRegister.setOnClickListener {
            val username = registerBinding.etUsername.text.toString()
            val password = registerBinding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por Favor Llena Todos los Campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(username, password)
            RetrofitClient.getClient().create(ApiService::class.java)
                .register(user).enqueue(object : Callback<ResponseMessage> {
                    override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                        val msg = if (response.isSuccessful) {
                            response.body()?.message ?: "Registrado"
                        } else {
                            "Registro Fallido."
                        }
                        registerBinding.tvStatus.text = msg
                    }
                    override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                        registerBinding.tvStatus.text = "Problemas de Conexión al Servidor.\nError: ${t.message}"
                    }
                })
        }

        registerBinding.tvGoToLogin.setOnClickListener {
            showLogin()
        }
    }
}
