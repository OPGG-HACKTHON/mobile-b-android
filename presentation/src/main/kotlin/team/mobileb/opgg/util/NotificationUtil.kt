package team.mobileb.opgg.util

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import team.mobileb.opgg.R
import kotlin.random.Random

@Suppress("DEPRECATION")
object NotificationUtil {
    private fun builder(context: Context, channelId: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, channelId)
        } else NotificationCompat.Builder(context)

    fun createChannel(context: Context, name: String, description: String = name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager(context).createNotificationChannelGroup(
                NotificationChannelGroup(
                    name,
                    name
                )
            )

            val channelMessage =
                NotificationChannel(name, name, NotificationManager.IMPORTANCE_HIGH).apply {
                    this.description = description
                    group = name
                    enableVibration(false)
                }
            getManager(context).createNotificationChannel(channelMessage)
        }
    }

    private fun getManager(context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun showNormalNotification(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        content: String,
        icon: Int,
        isOnGoing: Boolean,
        showTimestamp: Boolean,
    ) {
        getManager(context).notify(
            id,
            getNormalNotification(
                context,
                channelId,
                title,
                content,
                icon,
                isOnGoing,
                showTimestamp
            ).build()
        )
    }

    private fun getNormalNotification(
        context: Context,
        channelId: String,
        title: String,
        content: String,
        @DrawableRes icon: Int,
        isOnGoing: Boolean,
        showTimestamp: Boolean,
    ) = builder(context, channelId)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(icon)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setOngoing(isOnGoing)
        .setWhen(System.currentTimeMillis())
        .setShowWhen(showTimestamp)

    fun noticeGameStart(context: Context) {
        showNormalNotification(
            context = context,
            id = Random.nextInt(),
            channelId = context.getString(R.string.app_name),
            title = context.getString(R.string.notification_gamestart_title),
            content = context.getString(R.string.notification_gamestart_content),
            icon = R.drawable.ic_round_alert_25,
            isOnGoing = false,
            showTimestamp = true
        )
    }
}
