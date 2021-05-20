package com.thirtyeight.thirtyeight.util.video

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.SingleSampleMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.MimeTypes
import com.thirtyeight.thirtyeight.data.model.LessonMediaSourceUri

class BuildMediaSource(context: Context) {
    private val dataSourceFactory = DefaultDataSourceFactory(context, "sample")

    private val textFormat = Format.createTextSampleFormat(
        null,
        MimeTypes.TEXT_VTT,
        C.SELECTION_FLAG_DEFAULT,
        null
    )

    fun setMediaSourceWithSubs(lessonMediaSourceUri: LessonMediaSourceUri): MediaSource {
            val subtitlesSource = SingleSampleMediaSource.Factory(dataSourceFactory)
                .createMediaSource(lessonMediaSourceUri.sourceSubUri, textFormat, C.TIME_UNSET)

            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(lessonMediaSourceUri.sourceFileUri)

           return MergingMediaSource(mediaSource, subtitlesSource)
    }

    fun setAudioSource(mediaSource: MediaItem): MediaSource {
        val audioSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaSource)

        return MergingMediaSource(audioSource)
    }

}