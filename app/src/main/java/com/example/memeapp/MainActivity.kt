package com.example.memeapp

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeapp.R.*
import com.example.memeapp.R.id.memeImage
import com.example.memeapp.R.id.progressBar
import com.example.memeapp.util.ConnectionManager
import org.json.JSONObject


class MainActivity : AppCompatActivity() {



     var imageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        if (!(ConnectionManager().checkConnectivity(this)))
        {

            val dialog=AlertDialog.Builder(this)
            dialog.setTitle("No Internet")
            dialog.setMessage("Please Connect to Internet")

            dialog.setPositiveButton("Ok"){
                    text,listener->

            }
            dialog.setNegativeButton("Cancel")
            {
                    text,listener->
            }
            dialog.create()
            dialog.show()
        }
        else
        {
            val dialog=AlertDialog.Builder(this)
            dialog.setTitle("Greetings from Aman Verma")
            dialog.setMessage("Welcome To Memonia")

            dialog.setPositiveButton("Ok"){text,listener->

            }
            dialog.setNegativeButton("Cancel")
            {
                    text,listener->
            }
            dialog.create()
            dialog.show()
        }



        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        loadMeme()
    }

    private fun loadMeme() {



       var progressBar= findViewById<ProgressBar>(id.progressBar)
        progressBar.visibility= View.VISIBLE

        var memeImage= findViewById<ImageView>(id.memeImage)


        var url = "https://meme-api.herokuapp.com/gimme"


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                imageUrl= response.getString("url")

                Glide.with(this).load(imageUrl).listener(object :RequestListener<Drawable>
                {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        progressBar.visibility= View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        progressBar.visibility= View.GONE
                        return false
                    }

                }).into(memeImage)

            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()

            }
        )
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    fun nextMeme(view: View) {

loadMeme()
    }

    fun shareMeme(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT," CheckOut this New Meme $imageUrl")

        val chooser= Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)

    }
}