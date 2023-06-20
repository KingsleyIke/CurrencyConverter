package com.ex.applicationxxx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ex.applicationxxx.databinding.ActivityMainBinding

class MainActivityAP : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel
    private lateinit var binding: ActivityMainBinding

    //todo add progress bar
    //todo add image view
    //todo recycler view
    //todo repository
    //todo run imabckground thread
    //todo run i=with success and error
    //check difference lazy and lateinit
    //lookup services and their usage
    //how would you pass data from one activity to the next
    //todo tests


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

//        val btnupload: Button = findViewById(R.id.btn_getJoke)
//        val result: TextView = findViewById(R.id.result)

        Log.e("message", "mggg")

        binding.btnGetJoke.setOnClickListener {
            Log.i("ERRORR", "clicked")
            viewModel.fetchMultiData()
        }

//        viewModel.data.observe(this, Observer { value ->
//            binding.result.text = value
//        })

        viewModel.response.observe(this, Observer {

            when(it) {
                is Apiresponse.Success -> {
                    binding.result.text = it.lego.value
                    Glide.with(this)
                        .load(it.lego.url)
                        .into(binding.image)
                }
                is Apiresponse.Error -> {
                    Toast.makeText(this, "Error getting jokes", Toast.LENGTH_LONG).show()
                }
            }


        })


        val recyclerView = binding.recyc
        val recAdapter = MyAdapter(viewModel.dummmyList, this)

        recyclerView.adapter = recAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)



    }
}