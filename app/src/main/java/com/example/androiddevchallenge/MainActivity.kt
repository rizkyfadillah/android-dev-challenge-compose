/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.example.androiddevchallenge.ui.detail.DetailScreen
import com.example.androiddevchallenge.ui.home.HomeScreen
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {

    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                AppContent(navigationViewModel)
            }
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}

//@Preview("Light Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun LightPreview(navigationViewModel: NavigationViewModel? = null) {
//    MyTheme {
//        MyApp(navigationViewModel)
//    }
//}

//@Preview("Dark Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun DarkPreview(navigationViewModel: NavigationViewModel? = null) {
//    MyTheme(darkTheme = true) {
//        MyApp(navigationViewModel)
//    }
//}

// Start building your app here!
@Composable
fun AppContent(navigationViewModel: NavigationViewModel) {
    Crossfade(navigationViewModel.currentScreen, animationSpec = tween()) { screen ->
        when (screen) {
            Screen.Home -> HomeScreen(navigateTo = navigationViewModel::navigateTo)
            is Screen.Detail -> DetailScreen(
                screen.name,
                screen.image
            ) { navigationViewModel.onBack() }
        }
    }
}
