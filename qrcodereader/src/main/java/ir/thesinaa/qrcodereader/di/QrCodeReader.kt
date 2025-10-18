package ir.thesinaa.qrcodereader.di

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow

interface QrCodeReader {
    fun getPreviewView(): PreviewView
    fun scan(lifecycleOwner: LifecycleOwner): Flow<String>
}