package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.TeeAudioProcessor
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentAudioLessonTabBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import com.thirtyeight.thirtyeight.util.video.BuildMediaSource
import com.thirtyeight.thirtyeight.util.video.MediaPlayerClass
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.nio.ByteBuffer

class AudioLessonTabFragment : ViewStateViewModelFragment<FragmentAudioLessonTabBinding, LessonViewModel>() {
    private lateinit var mediaPlayer: MediaPlayerClass
    private lateinit var player: SimpleExoPlayer

    override val layoutRes: Int
        get() = R.layout.fragment_audio_lesson_tab

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentAudioLessonTabBinding = FragmentAudioLessonTabBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = SimpleExoPlayer.Builder(requireContext()).build()
        mediaPlayer = MediaPlayerClass(player)

        viewModel.setLessonAudio()
    }

    override fun onStart() {
        super.onStart()

        initPlayer()
    }

    override fun onResume() {
        super.onResume()

        initPlayer()
    }

    override fun onPause() {
        super.onPause()

        releasePlayer()
    }

    override fun onStop() {
        super.onStop()

        releasePlayer()
    }

    private fun initPlayer() {
        viewModel.lessonSingleMediaSource.observe(viewLifecycleOwner, {
            mediaPlayer.setPlayerMediaSource(BuildMediaSource(requireContext()).setAudioSource(it))
        })

        mediaPlayer.initPlayer(binding.lessonAudioPlayer, 0, 0L)
    }

    private fun releasePlayer() {
        mediaPlayer.releasePlayer()
    }
}