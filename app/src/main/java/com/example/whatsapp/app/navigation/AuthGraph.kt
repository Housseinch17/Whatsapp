package com.example.whatsapp.app.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.whatsapp.feature.authentication.AuthenticationRoot

fun NavGraphBuilder.authGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    navigation<NavigationGraphs.AuthGraph>(startDestination = NavigationScreens.AuthenticationScreen) {
        composable<NavigationScreens.AuthenticationScreen> {
            AuthenticationRoot(
                modifier = modifier,
                navigateToChat = {
//                    navHostController.navigate(
//                        NavigationGraphs.ChatGraph
//                    ) {
//                        popUpTo(NavigationGraphs.AuthGraph) {
//                            inclusive = true
//                        }
//                    }
                }
            )
        }
    }
}