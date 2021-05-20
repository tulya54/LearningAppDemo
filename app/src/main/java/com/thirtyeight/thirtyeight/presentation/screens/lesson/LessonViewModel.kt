package com.thirtyeight.thirtyeight.presentation.screens.lesson

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import com.thirtyeight.thirtyeight.data.model.LessonMediaSourceUri
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.presentation.screens.main.discover.DiscoverUiAction
import com.thirtyeight.thirtyeight.presentation.screens.main.discover.DiscoverViewState
import com.thirtyeight.thirtyeight.presentation.screens.main.discover.DiscoverWish
import com.thirtyeight.thirtyeight.presentation.screens.main.discover.NavigateTo
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

class LessonViewModel(
    coroutineContextProvider: CoroutineContextProvider,
    stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<DiscoverViewState, DiscoverUiAction, DiscoverWish>(coroutineContextProvider, stringResourceMapper) {

    private var getVideo: String = ""
    private var getAudio: String = ""
    private var getSubtitles: String = ""
    private val _lessonMediaSource = MutableLiveData<LessonMediaSourceUri>()
    val lessonMediaSource: LiveData<LessonMediaSourceUri> = _lessonMediaSource

    private val _lessonSingleMediaSource = MutableLiveData<MediaItem>()
    val lessonSingleMediaSource: LiveData<MediaItem> = _lessonSingleMediaSource

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    fun setLessonVideo() {
        getVideo = "https://thepaciellogroup.github.io/AT-browser-tests/video/ElephantsDream.mp4"
        getSubtitles = "https://thepaciellogroup.github.io/AT-browser-tests/video/subtitles-en.vtt"

        val videoIntoUri = MediaItem.fromUri(Uri.parse(getVideo))
        val subsIntoUri = Uri.parse(getSubtitles)

        val mediaItems = LessonMediaSourceUri(videoIntoUri, subsIntoUri)
        _lessonMediaSource.value = mediaItems
    }

    fun setLessonAudio() {
        getAudio = "https://www.voiptroubleshooter.com/open_speech/american/OSR_us_000_0010_8k.wav"

        val audioIntoUri = MediaItem.fromUri(Uri.parse(getAudio))
        _lessonSingleMediaSource.value = audioIntoUri
    }


    override fun getInitialViewState() =
        DiscoverViewState(tmp = "")

    override val reducer: Reducer<DiscoverViewState, DiscoverWish>
        get() = DiscoverReducer()

    override fun onCreate() {
        super.onCreate()
    }

    override fun processUiAction(uiAction: DiscoverUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
        }
    }

    private class DiscoverReducer : Reducer<DiscoverViewState, DiscoverWish> {
        override fun invoke(viewState: DiscoverViewState, wish: DiscoverWish): DiscoverViewState {
            return viewState
        }
    }
}