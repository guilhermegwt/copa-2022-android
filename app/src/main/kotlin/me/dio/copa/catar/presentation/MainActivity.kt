package me.dio.copa.catar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import me.dio.copa.catar.extensions.observe
import me.dio.copa.catar.notification.scheduler.extensions.NotificationMatcher
import me.dio.copa.catar.ui.theme.Copa2022Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeActions()
        setContent {
            Copa2022Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state by viewModel.state.collectAsState()
                    MainScreen(state.matches, viewModel::toggleNotification)
                }
            }
        }
    }

    private fun observeActions() {
        viewModel.action.observe(this) { action ->
            when (action) {
                is MainUiAction.MatchesNotFound -> TODO()
                MainUiAction.Unexpected -> TODO()
                is MainUiAction.DisableNotification ->
                    NotificationMatcher.cancel(applicationContext, action.match)
                is MainUiAction.EnableNotification ->
                    NotificationMatcher.start(applicationContext, action.match)
            }
        }
    }

}
