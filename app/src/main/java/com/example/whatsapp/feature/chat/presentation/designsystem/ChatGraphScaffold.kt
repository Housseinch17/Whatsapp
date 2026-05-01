package com.example.whatsapp.feature.chat.presentation.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.whatsapp.feature.chat.presentation.data.ChatConstants

@Composable
fun ChatGraphScaffold(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    isBottomBarVisible: Boolean = true,
    content: @Composable (PaddingValues) -> Unit,
) {
    val currentDestination = navHostController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if(isBottomBarVisible) {
                ChatGraphBottomBar(
                    chatScreensList = ChatConstants.chatScreensList,
                    currentDestination = currentDestination,
                    navigateToScreen = { screens ->
                        navHostController.navigate(screens) {
                            //findStartDestination here means that the backstack will always have the first which is chats
                            //so if we navigate to call we will have: chats -> call so any back button will take us to chats screen
                            //and if we are at chats and click chats nothing happens since using launchedSingleTop = true
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}