package ntnu.katarzsz.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = (0..100).random()
        val intent = Intent()
        intent.putExtra("upperLimit", "100")
        intent.putExtra("randomNumber", value.toString())
        intent.putExtra("test", "test:CCC")
        //Toast.makeText(this, value.toString(), Toast.LENGTH_LONG).show()
        setResult(RESULT_OK, intent)
        finish()
    }


}