package com.tjEnterprises.phase10Counter.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tjEnterprises.phase10Counter.data.local.models.SettingsModel
import com.tjEnterprises.phase10Counter.data.local.repositories.SettingsRepository.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AboutScreenViewModel @Inject constructor(settingsRepository: SettingsRepositoryImpl) : ViewModel() {
    private val _defaultDontChangeUiWideScreen = SettingsModel().dontChangeUiOnWideScreen
    val dontChangeUiWideScreen: StateFlow<Boolean> = settingsRepository.settingsModelFlow.map { settings ->
        settings.dontChangeUiOnWideScreen
    }.catch { _defaultDontChangeUiWideScreen }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _defaultDontChangeUiWideScreen
    )
}