package com.example.sample.i

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class Rove2LiveVideoViewModel : ViewModel() {
    abstract val stateLiveData: MutableLiveData<CameraR2Status>

    abstract fun getR2CameraConnectedOrNot()
}

class RoveR2UseCase {

    interface Callback {
        fun onSuccessSettingResponse(response: SettingsResponse?)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback;
    }
}

class RoveR2LiveVideoUseCase {

    interface Callback {
        fun onSuccessAudioMode(response: CardStatusResponse?)
        fun onSuccessUpdatedRecordingStatus(response: CardStatusResponse?)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback;
    }
}


data class CardStatusResponse(val status: String?)

data class SettingsResponse(
    val statuses: List<String>,
    val commands: List<String>,
)

abstract class CameraR2Status(val status: String)

class AudioModeStatus(status: String) : CameraR2Status(status)

class RecordingStatus(status: String) : CameraR2Status(status)