package ntnu.katarzsz.flagapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onClickStartChangeFlagActivity(v: View?) {
        val intent = Intent("ntnu.katarzsz.flagapplication.CHANGE_FLAG")
        startActivity(intent)
    }
}
