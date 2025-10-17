package ir.thesinaa.videoplayer.player

import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoPlayerDefault @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : VideoPlayer {
    private val _playing = MutableStateFlow(false)
    override val playing: Flow<Boolean> get() = _playing

    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: Flow<Long> get() = _currentPosition

    private val playerScope = CoroutineScope(Dispatchers.Main)
    private var positionJob: Job? = null

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playing.value = isPlaying
                if (isPlaying) startPositionUpdates() else stopPositionUpdates()
            }
        })
    }

    override fun load(source: String) {
        exoPlayer.stop()
        exoPlayer.clearMediaItems()

        val mediaItem = MediaItem.Builder()
            .setUri(source)
            .setMimeType(MimeTypes.VIDEO_MP4)
            .build()

        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun play() {
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun stop() {
        exoPlayer.stop()
        stopPositionUpdates()
    }

    override fun reset() {
        exoPlayer.seekTo(0)
        _currentPosition.value = 0
    }

    override fun quickForward(ms: Long) {
        val target = exoPlayer.currentPosition + ms
        seekTo(target)
    }

    override fun quickBackward(ms: Long) {
        val target = (exoPlayer.currentPosition - ms).coerceAtLeast(0)
        seekTo(target)
    }

    override fun seekTo(positionMs: Long) {
        exoPlayer.seekTo(positionMs)
        _currentPosition.value = positionMs
    }

    override fun release() {
        stopPositionUpdates()
        exoPlayer.release()
    }

    private fun startPositionUpdates() {
        if (positionJob?.isActive == true) return
        positionJob = playerScope.launch {
            while (true) {
                _currentPosition.value = exoPlayer.currentPosition
                delay(500L)
            }
        }
    }

    private fun stopPositionUpdates() {
        positionJob?.cancel()
        positionJob = null
    }

    override fun getExoPlayer(): ExoPlayer = exoPlayer
}
