package fr.isen.dobosz.projet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.translationMatrix
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import java.util.regex.Pattern


class RegistrationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    //public var name:String = ""
     var password: String = ""
     var email: String = ""
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_registration)
        initializeSpinner()
        dateText.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                dateText.clearFocus()
                val dialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        onDateChoose(year, month, dayOfMonth)
                    },
                    1970,
                    1,
                    1)
                dialog.show()
            }
        }




        var database = FirebaseDatabase.getInstance()
//        var myRef = database.getReferenceFromUrl("user/Email")
  //      myRef.setValue("HelloWorld");
        registerButton.setOnClickListener() {
            if(checkForm()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            System.out.println(passwField.getText().toString() + confirmPasswField.getText().toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_home -> intent = Intent(this, HomeActivity::class.java)
        }
        startActivity(intent)
        return true
        //return super.onOptionsItemSelected(item)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = mAuth?.getCurrentUser()
        //updateUI(currentUser)

    }

    fun checkPasswords(): Boolean {
        if (passwField.getText().toString().length == confirmPasswField.getText().toString().length) { //same length
            if (passwField.getText().toString().length < 8) { //length not enough
                Toast.makeText(
                    this,
                    "Passwords must contain at least 8 characters",
                    Toast.LENGTH_LONG
                ).show()
                return false
            } else if (passwField.getText().toString() != confirmPasswField.getText().toString()) { //different passw
                Toast.makeText(this, "The passwords must be the same", Toast.LENGTH_LONG).show()
                return false
            } else { //password are the same
                var countNumber = false //passw does not contain number
                var countCapLetter = false //passw does not capital letter
                for (letter in passwField.getText().toString()) { //Check number in passw
                    try { //is letter a number ?
                        // val i = Integer.parseInt(letter.toString())
                        //System.out.println("C'est un entier")
                        informations.setText("")
                        countNumber = true
                    } catch (e: Exception) {
                        //System.out.println("Je ne suis pas un entier, et alors ca te derange ?")
                    }
                    if (letter == letter.toUpperCase()) { // letter upCase
                        // Vérifier si le texte est en majuscule
                        countCapLetter = true
                    }
                }
                if (countNumber && countCapLetter)
                    return true
                else {
                    informations.setText(R.string.stringPasswordInfo)
                    return false
                }
            }
        } else { //password not same length
            Toast.makeText(this, "The passwords must be the same", Toast.LENGTH_LONG).show()
            return false
        }
    }


    fun checkFields(): Boolean {
        if (nameField.getText().toString() == "" || surnameField.getText().toString() == "" ||
            emailField.getText().toString() == "" || passwField.getText().toString() == "" ||
            confirmPasswField.getText().toString() == "" || dateText.getText().toString() == "") {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
            return false
        } else
            return true
    }

    fun checkMail(): Boolean {
        val masque =
            "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+" + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$"
        val pattern = Pattern.compile(masque)
        val controler = pattern.matcher(emailField.getText().toString())
        if (controler.matches()) {
            //System.out.println("Ok")
            return true

        } else {
            informations.setText(R.string.emailError)
            return false
        }
    }

    fun checkNames(): Boolean {
        for (letter in nameField.getText().toString()) {
            if (letter.toString() == " ") {
                informations.setText(R.string.noSpace)
                return false
            }
        }
        for (letter in surnameField.getText().toString()) {
            if (letter.toString() == " ") {
                informations.setText(R.string.noSpace)
                return false
            }
        }
        return true
    }
    fun checkSecretAnswer(): Boolean {
        for (letter in secretQuestionEdit.getText().toString()) {
            if (letter.toString() == " ") {
                informations.setText(R.string.noSpaceSecretAnswer)
                return false
            }
        }
        return true
    }


    fun checkForm(): Boolean{
        if (checkFields()) {
            System.out.println("FIELDS NOT EMPTY")
            if (checkNames()) {

                System.out.println("NAMES OK")
                if (checkMail()) {
                    System.out.println("email OK")
                    if (checkPasswords()) {
                        System.out.println("passw OK")
                        if (checkSecretAnswer()) {
                            System.out.println("SAnswer OK")
                            // change activity
//                            password = passwField.getText().toString()
//                            email = emailField.getText().toString()
//
//                            mAuth = FirebaseAuth.getInstance()
//                            mAuth!!.createUserWithEmailAndPassword(
//                                email,
//                                password
//                            )
//                                .addOnCompleteListener(
//                                    this
//                                ) { task ->
//                                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
//                                        //Log.d(FragmentActivity.TAG, "createUserWithEmail:success")
//                                        informations.setText(R.string.ok)
//                                        val user = mAuth!!.currentUser
//
//                                        //updateUI(user)
//                                    } else { // If sign in fails, display a message to the user.
//                                        val TAG = "EmailPassword"
//                                        Log.w(
//                                            TAG, "createUserWithEmail:failure",
//                                            task.exception
//                                        )
//                                        Toast.makeText(
//                                            this@RegistrationActivity, "Authentication failed.",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        informations.setText(R.string.emailAlreadyUsed)
//                                        //updateUI(null)
                                    //}
                                    // ...
                                //}
                            saveNewUser()
                            return true
                        }
                    }
                }
            }
            //if checkPasswords()
        }
    return false

    }

    fun initializeSpinner(){
        val spinner = findViewById<Spinner>(R.id.secretQuestionSpinner)
    val adapter = ArrayAdapter.createFromResource(this, R.array.secretQuestion,android.R.layout.simple_spinner_item).also{ adapter ->
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter
    }


    secretQuestionSpinner.onItemSelectedListener = this


    }

    fun onDateChoose(year: Int, month: Int, day: Int) {
        dateText.setText(String.format("%02d/%02d/%04d",day,month+1,year))
        Toast.makeText(this,
            "date : ${dateText.text.toString()}",
            Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        System.out.println("ELEMENT SELECTIONNE"+parent!!.getItemAtPosition(position))
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback

    }

    fun saveNewUser(){
        val jsonObj = JSONObject()
        val jsonArray = JSONArray()

        jsonObj.put("name",nameField.getText().toString())
        jsonObj.put("surname",surnameField.getText())
        jsonObj.put("email",surnameField.getText())
        val hash = passwField.text.toString().hashCode()

        System.out.println("HASH"+hash)
        //jsonObj.put("password",hash)
        jsonObj.put("birthDate",dateText.getText())
        jsonObj.put("secretQuestion",secretQuestionSpinner.selectedItem)
        jsonObj.put("secretAnswer",secretQuestionEdit.getText())
        jsonArray.put(jsonObj)
        val sharedNewUser = this.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE) ?: return
        with(sharedNewUser.edit()) {
            putString("userInfo", jsonArray.toString())
            //putBoolean("saveState",true)
            commit()
        }
        System.out.println("JSON"+sharedNewUser)
    }

    fun String.toSHA(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}
