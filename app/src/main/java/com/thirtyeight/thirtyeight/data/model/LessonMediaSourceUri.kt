package com.thirtyeight.thirtyeight.data.model

import android.net.Uri
import com.google.android.exoplayer2.MediaItem

data class LessonMediaSourceUri(
    val sourceFileUri: MediaItem,
    val sourceSubUri: Uri
)