package team.mobileb.opgg.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

@Module
@InstallIn(ViewModelComponent::class)
object StompModule {
    private const val BaseUrl =
        "ws://gdok.site:3724/socket/websocket"

    @Provides
    @ViewModelScoped
    fun provideStomp(): StompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BaseUrl)
}
