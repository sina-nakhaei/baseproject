package ir.thesinaa.qrcodereader.di.component

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun QrCodeContainer(
    view: PreviewView,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = { view },
        modifier = modifier
            .fillMaxWidth()
    )
}