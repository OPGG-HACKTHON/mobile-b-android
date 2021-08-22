package team.mobileb.opgg.util.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun toast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, length).show()
}

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    toast(this, message, length)
}
