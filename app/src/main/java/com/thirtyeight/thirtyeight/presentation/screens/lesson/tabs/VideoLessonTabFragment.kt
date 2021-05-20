package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.nikoloz14.myextensions.makeGone
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentVideoLessonTabBinding
import com.thirtyeight.thirtyeight.presentation.ext.toDp
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import com.thirtyeight.thirtyeight.util.video.BuildMediaSource
import com.thirtyeight.thirtyeight.util.video.MediaPlayerClass
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoLessonTabFragment : ViewStateViewModelFragment<FragmentVideoLessonTabBinding, LessonViewModel>() {
    private lateinit var mediaPlayer: MediaPlayerClass
    private lateinit var player: SimpleExoPlayer

    override val layoutRes: Int
        get() = R.layout.fragment_video_lesson_tab

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentVideoLessonTabBinding = FragmentVideoLessonTabBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = SimpleExoPlayer.Builder(requireContext()).build()
        mediaPlayer = MediaPlayerClass(player)

        viewModel.setLessonVideo()

        binding.lessonVideoPlayer.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, view.height, 8.toDp().toFloat());
            }
        }
        binding.lessonVideoPlayer.clipToOutline = true

        mediaPlayer.setPlayerListener(object : Player.EventListener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)

                videoPlayerControls(isPlaying)
            }
        })

        startVideoButton()
    }

    private fun startVideoButton() {
//        binding.lessonVideoPlayer.hideController()
        binding.playButton.setOnClickListener {
            player.play()
            binding.lessonVideoPlayer.useController = true
            binding.playButton.makeGone()
        }
    }

    private fun videoPlayerControls(isPlaying: Boolean) {
        val pauseBottom = binding.lessonVideoPlayer.findViewById<ImageView>(R.id.exo_pause_bottom)

        if (isPlaying) {
            pauseBottom.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.video_player_pause))
            pauseBottom.setOnClickListener {
                player.pause()
            }
        } else {
            pauseBottom.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.video_player_play))
            pauseBottom.setOnClickListener {
                player.play()
            }
        }
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
        viewModel.lessonMediaSource.observe(viewLifecycleOwner, {
            mediaPlayer.setPlayerMediaSource(BuildMediaSource(requireContext()).setMediaSourceWithSubs(it))
        })

        mediaPlayer.initPlayer(binding.lessonVideoPlayer, 0, 0L)
    }

    private fun releasePlayer() {
        mediaPlayer.releasePlayer()
    }
}