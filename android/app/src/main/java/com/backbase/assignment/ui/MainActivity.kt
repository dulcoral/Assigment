package com.backbase.assignment.ui

import android.os.Bundle
import android.widget.Toast
import com.backbase.assignment.R
import com.backbase.assignment.utils.MovieListener
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(), MovieListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showErrorMessage(errorMessage: String?) {
        if (errorMessage.isNullOrBlank().not()) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

}
