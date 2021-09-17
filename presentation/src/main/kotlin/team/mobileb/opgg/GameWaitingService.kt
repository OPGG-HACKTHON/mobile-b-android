package team.mobileb.opgg

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import team.mobileb.opgg.util.ColorUtil
import team.mobileb.opgg.util.NotificationUtil
import java.util.UUID

@HiltAndroidApp
class GameWaitingService : Application() {
    companion object {
        val DeviceId = UUID.randomUUID().toString()
    }

    override fun onCreate() {
        super.onCreate()
        ColorUtil
        NotificationUtil.createChannel(
            context = applicationContext,
            name = getString(R.string.app_name)
        )
    }
}
