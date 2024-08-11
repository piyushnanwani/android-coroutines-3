package com.piyush.coroutineex3

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        CoroutineScope(Dispatchers.Main).launch {
            fetchData2()
        }


    }

    // crate 2 suspend functions getFirstData() and getSecondData() here with delay of 1 second and they return String
    // create a suspend function fetchData() which calls getFirstData() and getSecondData() in parallel and returns a concatenated string
    // call fetchData() from onCreate() and print the result in logcat
    suspend fun getFirstData(): String {
        delay(1000)
        return "First Data"
    }

    suspend fun getSecondData(): String {
        delay(1000)
        return "Second Data"
    }


    suspend fun fetchData() {
        var firstData: String = ""
        val job1 = CoroutineScope(Dispatchers.IO).launch {
            firstData = getFirstData()
        }
        job1.join() // wait for job1 to finish
        Log.d("fetchData", firstData)
    }

    // Now rewrite above function fetchData with async await
    suspend fun fetchData2() {
        val firstData = CoroutineScope(Dispatchers.IO).async {
            getFirstData()
        }
        val secondData = CoroutineScope(Dispatchers.IO).async {
            getSecondData()
        }
        Log.d("fetchData", "${firstData.await()} ${secondData.await()}")
    }

}

