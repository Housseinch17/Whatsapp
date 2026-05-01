package com.example.whatsapp.feature.chat.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.whatsapp.app.navigation.NavigationScreens
import com.example.whatsapp.core.presentation.designsystem.theme.robotoFamily

@Composable
fun ChatGraphBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    chatScreensList: List<NavigationScreens>,
    navigateToScreen: (NavigationScreens) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        chatScreensList.forEach { screen ->
            ChatBottomBarItem(
                modifier = Modifier.weight(1f),
                currentScreen = screen,
                isSelected = currentDestination?.hasRoute(screen::class) == true,
                onScreenSelect = {
                    navigateToScreen(screen)
                }
            )
        }
    }
}

@Composable
fun ChatBottomBarItem(
    modifier: Modifier = Modifier,
    currentScreen: NavigationScreens,
    isSelected: Boolean,
    onScreenSelect: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onScreenSelect)
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(currentScreen.icon),
            contentDescription = currentScreen.name.asString(),
            tint = if (isSelected) MaterialTheme.colorScheme.secondary else Color(0xFF979797)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = currentScreen.name.asString(),
            style = TextStyle(
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.1.sp,
                color = if (isSelected) MaterialTheme.colorScheme.secondary else Color(0xFF979797)
            )
        )
    }
}