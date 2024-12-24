package com.breera.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun LoadImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Int = 0,
    contentScale: ContentScale = ContentScale.Crop,
    size: Dp = 60.dp
) {
    // State for image loading result
    var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
    // Load the icon,
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            imageLoadResult =
                if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("Invalid image size"))
                }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    // Display the image or a loading spinner,
    when (val result = imageLoadResult) {
        null -> {
            CircularProgressIndicator(color = Color.Black, modifier = modifier.size(size))
        }

        else -> {
            Image(
                painter = if (result.isSuccess) painter else painterResource(placeholder),
                contentDescription = contentDescription,
                contentScale = if (result.isSuccess) contentScale else ContentScale.Fit,
                modifier = modifier.size(size)
            )
        }
    }
}
