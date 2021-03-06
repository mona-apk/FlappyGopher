package com.kamiapk.flappygopher

import android.annotation.TargetApi
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

class SoundManager constructor(context: Context) {


    private var soundPool: SoundPool? = null


    companion object {

        var SOUND_WING = 0
        var SFX_DIE = 0
        var SFX_HIT = 0
        var SFX_POINT = 0

        var INSTANCE:SoundManager? = null
        fun getInstance(context: Context) =
            INSTANCE ?: SoundManager(context).also {
                INSTANCE = it
            }

    }

    init {
        createSoundPool()
        loadSoundIDs(context)
    }

    private fun createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool()
        } else {
            createOldSoundPool()
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNewSoundPool() {
        val attributes = AudioAttributes.Builder().apply {
            setUsage(AudioAttributes.USAGE_GAME)
            setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)

        }.build()

        soundPool = SoundPool.Builder().apply {
            setMaxStreams(2)
            setAudioAttributes(attributes)
        }.build()
    }


    private fun createOldSoundPool() {
        soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
    }

    private fun loadSoundIDs(context:Context) {
        soundPool?.let {
            println("サウンドファイルロード")
            SOUND_WING = it.load(context, R.raw.sfx_wing, 1)
            SFX_DIE = it.load(context, R.raw.sfx_die, 1)
            SFX_HIT = it.load(context, R.raw.sfx_hit, 1)
            SFX_POINT =  it.load(context, R.raw.sfx_point, 1)
        }
    }


    fun playSound(soundID:Int) {
        soundPool?.let{
            it.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f)
            println("サウンド再生")
        }
    }


    fun close() {
        soundPool?.release()
        soundPool = null
    }
}