package ntnu.katarzsz.myapplication

import android.R
import android.R.menu
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class MainActivity : ComponentActivity() {
    val firstname = "Kasia";
    val lastname = "Szlejter";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_main)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

}
