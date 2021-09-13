package team.mobileb.opgg.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

@Module
@InstallIn(ViewModelScoped::class)
object StompModule {
    private const val BaseUrl =
        "ws://ec2-18-222-138-73.us-east-2.compute.amazonaws.com:3724/socket/websocket"

    @Provides
    @ViewModelScoped
    fun provideStomp(): StompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BaseUrl)
}
