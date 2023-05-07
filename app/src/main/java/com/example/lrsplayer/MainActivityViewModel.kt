package com.example.lrsplayer

import androidx.lifecycle.ViewModel
import com.example.lrsplayer.until.Constants
import com.example.lrsplayer.until.Constants.LIGHT_THEME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor():ViewModel() {

    private val _currentMainThemeColors = MutableStateFlow(Constants.LIGHT_THEME_COLORS)
    val currentMainThemeColors = _currentMainThemeColors

    init {
        switchMainThemeColors(LIGHT_THEME)
    }

    fun switchMainThemeColors(theme:String){
        when(theme){
            LIGHT_THEME -> _currentMainThemeColors.value = Constants.LIGHT_THEME_COLORS
            else ->  _currentMainThemeColors.value = Constants.DARK_THEME_COLORS
        }
    }

}