package com.example.androiddevchallenge

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

enum class ScreenName { HOME, DETAIL }

sealed class Screen(val id: ScreenName) {
    object Home : Screen(ScreenName.HOME)
    class Detail(val name: String, val image: Int) : Screen(ScreenName.DETAIL)
}

fun <T> SavedStateHandle.getMutableStateOf(
    key: String,
    default: T,
    save: (T) -> Bundle,
    restore: (Bundle) -> T
): MutableState<T> {
    val bundle: Bundle? = get(key)
    val initial = if (bundle == null) {
        default
    } else {
        restore(bundle)
    }
    val state = mutableStateOf(initial)
    setSavedStateProvider(key) {
        save(state.value)
    }
    return state
}

private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_PET_NAME = "pet_name"
private const val SIS_PET_IMAGE = "pet_image"

/**
 * Convert a screen to a bundle that can be stored in [SavedStateHandle]
 */
private fun Screen.toBundle(): Bundle {
    return bundleOf(SIS_NAME to id.name).also {
        // add extra keys for various types here
        if (this is Screen.Detail) {
            it.putString(SIS_PET_NAME, name)
            it.putInt(SIS_PET_IMAGE, image)
        }
    }
}

private fun Bundle.getStringOrThrow(key: String) =
    requireNotNull(getString(key)) { "Missing key '$key' in $this" }


/**
 * Read a bundle stored by [Screen.toBundle] and return desired screen.
 *
 * @return the parsed [Screen]
 * @throws IllegalArgumentException if the bundle could not be parsed
 */
private fun Bundle.toScreen(): Screen {
    val screenName = ScreenName.valueOf(getStringOrThrow(SIS_NAME))
    return when (screenName) {
        ScreenName.HOME -> Screen.Home
        ScreenName.DETAIL -> {
            val name = getStringOrThrow(SIS_PET_NAME)
            val image = getInt(SIS_PET_IMAGE)
            Screen.Detail(name, image)
        }
    }
}

class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    var currentScreen: Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = SIS_SCREEN,
        default = Screen.Home,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set // limit the writes to only inside this class.

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    /**
     * Go back (always to [Home]).
     *
     * Returns true if this call caused user-visible navigation. Will always return false
     * when [currentScreen] is [Home].
     */
    @MainThread
    fun onBack(): Boolean {
        val wasHandled = currentScreen != Screen.Home
        currentScreen = Screen.Home
        return wasHandled
    }
}