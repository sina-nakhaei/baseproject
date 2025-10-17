package ir.thesinaa.videoplayer.player

import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.Flow

interface VideoPlayer {
    val playing: Flow<Boolean>
    val currentPosition: Flow<Long>
    fun getExoPlayer(): ExoPlayer
    fun load(source: String)
    fun play()
    fun pause()
    fun stop()
    fun reset()
    fun quickForward(ms: Long)
    fun quickBackward(ms: Long)
    fun seekTo(positionMs: Long)
    fun release()
}