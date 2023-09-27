package ntnu.katarzsz.flagapplication

import android.app.Activity
import android.os.Bundle
import android.view.View

class ChangeFlagActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_flag)
    }
    

    fun onClickAvsluttAktivitet(v: View?) {
        finish()
    }
}
