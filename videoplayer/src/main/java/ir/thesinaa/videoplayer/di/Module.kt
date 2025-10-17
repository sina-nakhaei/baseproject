package ir.thesinaa.videoplayer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.thesinaa.videoplayer.player.VideoPlayer
import ir.thesinaa.videoplayer.player.VideoPlayerDefault
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface Module {
    @Binds
    @Singleton
    fun bindVideoPlayer(v: VideoPlayerDefault): VideoPlayer
}