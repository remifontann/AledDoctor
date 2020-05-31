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

class HomeConnectedFragment: Fragment(){
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






        return view
    }



}