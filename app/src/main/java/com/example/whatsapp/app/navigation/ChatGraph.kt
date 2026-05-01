package com.example.whatsapp.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsapp.feature.chat.presentation.designsystem.ChatGraphScaffold
import com.example.whatsapp.feature.chat.presentation.ui.screens.chats.ChatScreenRoot

fun NavGraphBuilder.chatGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    navigation<NavigationGraphs.ChatGraph>(startDestination = NavigationScreens.ChatScreen) {
        composable<NavigationScreens.ChatScreen> {
            ChatScreenRoot(
                modifier = Modifier.fillMaxSize(),
                navHostController = navHostController,
                navigateToNewChat = {

                }
            )
        }
    }

    composable<NavigationScreens.StatusScreen> {
        ChatGraphScaffold(
            modifier = modifier,
            navHostController = navHostController
        ) { _ ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Status Screen",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

    composable<NavigationScreens.CallScreen> {
        ChatGraphScaffold(
            modifier = modifier,
            navHostController = navHostController
        ) { _ ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Call Screen",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

    composable<NavigationScreens.CameraScreen> {
        ChatGraphScaffold(
            modifier = modifier,
            navHostController = navHostController
        ) { _ ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Camera Screen",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

    composable<NavigationScreens.SettingsScreen> {
        ChatGraphScaffold(
            modifier = modifier,
            navHostController = navHostController
        ) { _ ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Settings Screen",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}