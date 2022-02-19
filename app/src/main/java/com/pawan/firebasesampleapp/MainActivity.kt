package com.pawan.firebasesampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    var db: FirebaseDatabase? = null
    var dataNodeRef: DatabaseReference? = null
    lateinit var data:TextView
    lateinit var changeData:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data = findViewById(R.id.data)
        changeData = findViewById(R.id.changeData)

        db = FirebaseDatabase.getInstance()
        //dataNodeRef = db!!.getReference("data")
        dataNodeRef = db!!.getReference("enter_name")

        dataNodeRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) data.text = "Value is : " + dataSnapshot.value
                Log.e("CCHH",dataSnapshot.value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    fun change(view: View?) {
        dataNodeRef!!.setValue(changeData.text.toString())
        changeData.setText("")
    }
}