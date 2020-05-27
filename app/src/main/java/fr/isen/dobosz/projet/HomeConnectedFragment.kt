package fr.isen.dobosz.projet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import fr.isen.dobosz.projet.HomeActivity.Companion.newTime
import fr.isen.dobosz.projet.R.layout.activity_home_connected
import org.json.JSONArray
import org.json.JSONObject

class HomeConnectedFragment: Fragment(), View.OnTouchListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(activity_home_connected, container, false)


        val b: ImageButton = view.findViewById(R.id.homeButton)
        b.setOnClickListener(){ }

        val c: ImageButton = view.findViewById(R.id.mapButton)
        c.setOnClickListener {
            val intent = Intent(activity!!.application, GoogleMapInfoWindowActivity::class.java)
            startActivity(intent)
        }

        val form: TextView = view.findViewById(R.id.accessFormTextViewClickable)
        form.setOnClickListener(){
        val intent = Intent(activity!!.application, StarActivity::class.java)
        startActivity(intent) }


        val layout:View = view.findViewById(R.id.layout)
        layout.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_UP -> {
                }

                MotionEvent.ACTION_DOWN -> {

                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : "+x)
                    System.out.println("y : "+y)
                    // is new time (click down)
                    val jsonArray = JSONArray() //create new
                    jsonArray.put("screen_home") //from starAct
                    save(jsonArray, x, y)
                    newTime = false

//                    if(newTime){
//                        val jsonArray = JSONArray()
//                        save(jsonArray, x, y)
//                        newTime = false
//                    }
//
//                    else{
//                        save(readPreviousClick(), x,y)
//                    }
                    //writeFile()
                }
                MotionEvent.ACTION_MOVE -> {

                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : "+x)
                    System.out.println("y : "+y)
//                    if(newTime){
//                        val jsonArray = JSONArray()
//                        jsonArray.put("screen_accueil")
//                        save(jsonArray, x, y)
//                        newTime = false
//                    }
//
//                    else{
//
//                    }
                    //writeFile()
                    save(readPreviousClick(), x,y)
                }
                MotionEvent.ACTION_CANCEL -> {

                }

                MotionEvent.ACTION_POINTER_DOWN -> {

                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : "+x)
                    System.out.println("y : "+y)
                    // is new time (click down)
                    val jsonArray = JSONArray() //create new
                    jsonArray.put("screen_home_pointer") //from starAct
                    save(jsonArray, x, y)
                    newTime = false
//                    if(newTime){
//                        val jsonArray = JSONArray()
//                        save(jsonArray, x, y)
//                        newTime = false
//                    }
//
//                    else{
                        save(readPreviousClick(), x,y)
//                    }
                    //writeFile()
                }
            }

            val sosButton:ImageButton = view.findViewById(R.id.sosButton)
            sosButton.setOnClickListener(){
                startCall()
            }
            true
        }



        return view
    }
    fun startCall(){
        val intentcall = Intent()
        intentcall.action = Intent.ACTION_CALL
        intentcall.data = Uri.parse("tel:+33698305732") // set the Uri
        startActivity(intentcall)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun save(jsonArray: JSONArray, x:Int,y:Int){
        val jsonObj = JSONObject()
        val millis = System.currentTimeMillis()

//Divide millis by 1000 to get the number of seconds.
        //Divide millis by 1000 to get the number of seconds.
        // val seconds = millis / 1000

        jsonObj.put("clickX",x)
        jsonObj.put("clickY",y)
        jsonObj.put("timeClickedMillis",millis-1585154297320-2306884067)
        jsonArray.put(jsonObj)
        val sharedPrefPosition = this.activity!!.getSharedPreferences("sharedPrefPosition", Context.MODE_PRIVATE) ?: return
        with(sharedPrefPosition.edit()) {
            putString("backupMicePos", jsonArray.toString())
            //putBoolean("saveState",true)
            commit()
        }
        System.out.println(jsonArray)
    }
    fun readPreviousClick():JSONArray{

        val sharedPrefPosition = this.activity!!.getSharedPreferences("sharedPrefPosition",Context.MODE_PRIVATE)
        val readString = sharedPrefPosition.getString("backupMicePos", "") ?:""
        val jsonArray = JSONArray(readString)
//        System.out.println(jsonArray)
        return(jsonArray)
    }

}