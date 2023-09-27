package ntnu.katarzsz.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onGenerateNumberClick(view: View) {
        val intent = Intent("ntnu.katarzsz.oving2.CHANGE_VIEW")
        startActivityForResult(intent, 1)
    }

    fun goToCalculator(view: View) {
        val intent = Intent("ntnu.katarzsz.oving2.CALCULATOR")
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val textView = findViewById<View>(R.id.text_view_random_number) as TextView
        textView.text = data.getStringExtra("randomNumber")
    }
}
