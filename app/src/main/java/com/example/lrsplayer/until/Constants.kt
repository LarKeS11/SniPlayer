package com.example.lrsplayer.until

import androidx.compose.ui.graphics.Color

object Constants {

    val DARK_THEME_COLORS = ThemeColors(
        main_background = Color(0xFF2F2F2F),
        title = Color(0xFFECECEC),
        second_main_background = Color(0xFF232222),
        sub_title = Color(0xFFECECEC),
        music_panel_color = Color(0xFF161616),
        image_picker_color = Color(0x99F3F3F3),
        playlist_card = Color(0xFFFFFFFF)
    )
    val LIGHT_THEME_COLORS = ThemeColors(
        main_background = Color(0xFFFFFFFF),
        title = Color(0xFF212121),
        second_main_background = Color(0xFFF7F7F9),
        sub_title = Color(0xFF7D848D),
        music_panel_color = Color(0xFFEBEBEB),
        image_picker_color = Color(0x99F3F3F3),
        playlist_card = Color(0xFFFFFFFF)
    )

    const val DARK_THEME = "dark_theme"
    const val LIGHT_THEME = "light_theme"

}

data class ThemeColors(
   val main_background:Color,
   val second_main_background:Color,
   val title:Color,
   val sub_title:Color,
   val music_panel_color:Color,
   val image_picker_color:Color,
   val playlist_card:Color
   )