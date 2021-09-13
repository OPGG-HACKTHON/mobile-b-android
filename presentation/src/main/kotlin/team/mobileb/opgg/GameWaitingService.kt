package team.mobileb.opgg

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.util.UUID

@HiltAndroidApp
class GameWaitingService : Application() {
    companion object {
        var DeviceId = UUID.randomUUID().toString()
    }
}
