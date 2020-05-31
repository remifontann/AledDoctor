package fr.isen.dobosz.projet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType.*
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    val goodIdentifier = ""
    val goodPassword = ""
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.validateButton).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
           // doLogin()

        }

        var passwShown = false

        toggleShowHidePasswButton.layoutParams.height = passwordEditText.height
        val a: Int = passwordEditText.height
        toggleShowHidePasswButton.layoutParams.width = a

        findViewById<ImageButton>(R.id.toggleShowHidePasswButton).setOnClickListener() {
            if (passwShown == false) {
                passwordEditText.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                passwShown = true
                toggleShowHidePasswButton.setBackgroundResource(R.drawable.hide_password)

            } else {
                passwordEditText.inputType = TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwShown = false
                toggleShowHidePasswButton.setBackgroundResource(R.drawable.show_password)

            }

        }

    }




    fun doLogin() {
        val password = passwordEditText.getText().toString()
        val email = emailEditText.getText().toString()


        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "signInWithEmail:success")
                    //val user = mAuth!!.currentUser

                    val sharedPrefLogs: SharedPreferences =
                        getSharedPreferences("isConnected", Context.MODE_PRIVATE)
                    sharedPrefLogs.edit().putBoolean("isConn", true).apply()

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    //updateUI(user)
                } else { // If sign in fails, display a message to the user.
                    Log.w("login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@LoginActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // ...
            }
    }

    fun autoLogin() {
        val stringId: String? = getValueString("prompt_email")
        val stringPassword: String? = getValueString("prompt_password")
        if (canLog(stringId.toString(), stringPassword.toString())) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    fun canLog(identifier: String, password: String): Boolean {
        return (identifier == goodIdentifier && password == goodPassword)
    }


    fun getValueString(key_name: String): String? {
        val sharedPrefLogs: SharedPreferences =
            getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        return sharedPrefLogs.getString(key_name, "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_home -> intent = Intent(this, HomeActivity::class.java)
        }
        startActivity(intent)
        return true
    }
}
