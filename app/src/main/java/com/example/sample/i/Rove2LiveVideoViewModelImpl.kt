package com.example.sample.i

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Rove2LiveVideoViewModelImpl(
    override val stateLiveData: MutableLiveData<CameraR2Status>,
    private val roveR2UseCase: RoveR2UseCase,
    private val roveR2LiveVideoUseCase: RoveR2LiveVideoUseCase,
    private val appPreference: AppPreference,
) : Rove2LiveVideoViewModel(),
    RoveR2UseCase.Callback,
    RoveR2LiveVideoUseCase.Callback {

    init {
        roveR2UseCase.setCallback(this)
        roveR2LiveVideoUseCase.setCallback(this)
    }

    override fun getR2CameraConnectedOrNot() {
        viewModelScope.launch {
            delay(1000)

            onSuccessAudioMode(CardStatusResponse("audio-mode: on"))
            onSuccessUpdatedRecordingStatus(CardStatusResponse("recording: on"))
            onSuccessAudioMode(CardStatusResponse("audio-mode: off"))
            onSuccessUpdatedRecordingStatus(CardStatusResponse("recording: off"))

            val settingsResponse = SettingsResponse(
                commands = listOf("2001", "2002", "2003", "2006", "2007", "2008"),
                statuses = listOf("recording-ok", "bad", "bad", "bad", "audio-ok", "bad"),
            )
            onSuccessSettingResponse(settingsResponse)
        }
    }

    override fun onSuccessAudioMode(response: CardStatusResponse?) {
        stateLiveData.value = AudioModeStatus(response?.status.toString())
    }

    override fun onSuccessUpdatedRecordingStatus(response: CardStatusResponse?) {
        stateLiveData.value = RecordingStatus(response?.status.toString())
    }

    override fun onSuccessSettingResponse(response: SettingsResponse?) {
        response?.let { settingsResponse ->
            settingsResponse.commands.forEachIndexed { index, command ->
                if (command == "2007") {
                    stateLiveData.value = AudioModeStatus(settingsResponse.statuses[index])
                }
                if (command == "2001") {
                    stateLiveData.value = RecordingStatus(settingsResponse.statuses[index])
                }
            }
        }
    }
}