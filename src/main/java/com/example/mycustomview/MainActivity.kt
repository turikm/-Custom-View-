package com.example.mycustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: MutableList<String> = ArrayList()
        list.add("Kotlin")
        list.add("Java")
        list.add("C++")
        list.add("HTML")
        list.add("JavaScript")
        list.add("PhP")

        customview.setData(list)
        customview.setTitle("Languages")
        submit.setOnClickListener{
            Toast.makeText(this,"Selected Data: "+ customview.getSelectedData().toString(),Toast.LENGTH_SHORT).show()
        }
    }
}