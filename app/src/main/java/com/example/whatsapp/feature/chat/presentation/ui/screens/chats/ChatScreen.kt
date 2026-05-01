package com.example.whatsapp.feature.chat.presentation.ui.screens.chats

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.whatsapp.R
import com.example.whatsapp.core.data.model.User
import com.example.whatsapp.core.presentation.designsystem.components.WhatsAppAsyncImage
import com.example.whatsapp.core.presentation.designsystem.components.WhatsAppHorizontalDivider
import com.example.whatsapp.core.presentation.designsystem.theme.WhatsappIcons
import com.example.whatsapp.core.presentation.designsystem.theme.gray3
import com.example.whatsapp.core.presentation.ui.ObserveAsEvents
import com.example.whatsapp.core.presentation.ui.formatTimestamp
import com.example.whatsapp.feature.chat.presentation.designsystem.ChatGraphScaffold
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ChatScreenRoot(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel(),
    navHostController: NavHostController,
    navigateToNewChat: () -> Unit,
) {
    val state by chatViewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(chatViewModel.events) { events ->
        when (events) {
            ChatEvents.NavigateToNewChat -> navigateToNewChat()
        }
    }

    ChatGraphScaffold(
        modifier = modifier,
        isBottomBarVisible = !(state.isEditSelected),
        navHostController = navHostController
    ) { innerPadding ->
        ChatScreen(
            modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            state = state,
            onActions = chatViewModel::onActions,
        )
    }
}

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatUiState,
    onActions: (ChatActions) -> Unit,
) {
    val context = LocalContext.current
    val activity = (context as? ComponentActivity) ?: return

    val whiteColor = Color.White.toArgb()
    val grayColor = MaterialTheme.colorScheme.background.toArgb()
    val blackColor = Color.Black.toArgb()


    val style = if (state.isEditSelected) {
        SystemBarStyle.light(scrim = whiteColor, darkScrim = blackColor)
    } else {
        SystemBarStyle.light(scrim = grayColor, blackColor)
    }

    LaunchedEffect(style) {
        activity.enableEdgeToEdge(statusBarStyle = style)
    }

    //when leaving screen reset color back to default statusBar = grayColor
    DisposableEffect(Unit) {
        onDispose {
            activity.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(scrim = grayColor, darkScrim = blackColor)
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ChatTopBar(
                modifier = Modifier,
                isEditSelected = state.isEditSelected,
                onEditClick = {
                    onActions(ChatActions.UpdateEditSelection(true))
                },
                onNewChatClick = {},
                onDoneClick = {
                    onActions(ChatActions.UpdateEditSelection(false))
                }
            )
        },
        bottomBar = {
            if (state.isEditSelected) {
                ChatBottomBar(
                    canArchiveOrReadOrDelete = state.canArchiveOrReadOrDelete,
                    onArchiveClick = {
                        onActions(ChatActions.OnArchiveAll)
                    },
                    onReadAllClick = {
                        onActions(ChatActions.OnReadAll)
                    },
                    onDeleteClick = {
                        onActions(ChatActions.OnDelete)
                    }
                )
            }
        }
    ) { innerPadding ->
        ChatList(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = if(state.isEditSelected) innerPadding.calculateBottomPadding() else 0.dp
                ),
            list = state.users,
            onMoreClick = {
                onActions(ChatActions.OnMoreClick(user = it))
            },
            onArchiveClick = {
                onActions(ChatActions.OnArchiveClick(user = it))
            },
            onSelectUser = {
                onActions(ChatActions.OnSelectUser(user = it))
            },
            selectedUsers = state.selectedUsers,
            isEditSelected = state.isEditSelected,
        )
    }
}

@Composable
fun ChatTopBar(
    modifier: Modifier = Modifier,
    isEditSelected: Boolean,
    onEditClick: () -> Unit,
    onNewChatClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    AnimatedContent(
        modifier = modifier
            .fillMaxWidth()
            .background(color = if (!isEditSelected) MaterialTheme.colorScheme.background else Color.White),
        targetState = isEditSelected,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
    ) { state ->
        if (!state) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.clickable(onClick = onEditClick),
                        text = stringResource(R.string.edit),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    IconButton(
                        onClick = onNewChatClick
                    ) {
                        Icon(
                            imageVector = WhatsappIcons.OpenNewChat,
                            contentDescription = stringResource(R.string.open_chat),
                            tint = Color.Unspecified
                        )
                    }
                }

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.chats),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            Column(
                modifier = Modifier.padding(top = 10.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            ) {
                Text(
                    modifier = Modifier.clickable(onClick = onDoneClick),
                    text = stringResource(R.string.done),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.chats),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun ChatBottomBar(
    modifier: Modifier = Modifier,
    canArchiveOrReadOrDelete: Boolean,
    onArchiveClick: () -> Unit,
    onReadAllClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarClickableText(
            text = stringResource(R.string.archive),
            canArchiveOrReadOrDelete = canArchiveOrReadOrDelete,
            onClick = onArchiveClick
        )

        BottomBarClickableText(
            text = stringResource(R.string.read_all),
            canArchiveOrReadOrDelete = canArchiveOrReadOrDelete,
            onClick = onReadAllClick
        )

        BottomBarClickableText(
            text = stringResource(R.string.delete),
            canArchiveOrReadOrDelete = canArchiveOrReadOrDelete,
            onClick = onDeleteClick
        )
    }
}

@Composable
fun BottomBarClickableText(
    modifier: Modifier = Modifier,
    text: String,
    canArchiveOrReadOrDelete: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier.clickable(onClick = onClick),
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(
            color = if (canArchiveOrReadOrDelete) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.gray3
        )
    )
}

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
    list: List<User>,
    onMoreClick: (User) -> Unit,
    onArchiveClick: (User) -> Unit,
    onSelectUser: (User) -> Unit,
    selectedUsers: List<User>,
    isEditSelected: Boolean,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(list, key = {
            it.uid
        }) { user ->
            val isSelected = selectedUsers.contains(user)
            ChatItem(
                modifier = Modifier.fillMaxWidth(),
                user = user,
                onMoreClick = {
                    onMoreClick(user)
                },
                onArchiveClick = {
                    onArchiveClick(user)
                },
                isEditSelected = isEditSelected,
                isSelected = isSelected,
                selectUser = {
                    onSelectUser(user)
                },
            )
        }
    }
}

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    user: User,
    onMoreClick: () -> Unit,
    onArchiveClick: () -> Unit,
    isEditSelected: Boolean,
    isSelected: Boolean,
    selectUser: () -> Unit
) {
    val actionWidth = 140.dp
    val actionWidthPx = with(LocalDensity.current) {
        actionWidth.toPx()
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Row(
            modifier = Modifier
                .width(actionWidth)
                .fillMaxHeight()
                .align(Alignment.TopEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFC6C6CC))
                    .clickable(onClick = onMoreClick),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(21.dp),
                    imageVector = WhatsappIcons.Dots,
                    contentDescription = stringResource(R.string.dots),
                    tint = Color.White
                )
                Text(
                    text = stringResource(R.string.more),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White
                    )
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF3E70A7))
                    .clickable(onClick = onArchiveClick),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = WhatsappIcons.Archive,
                    contentDescription = stringResource(R.string.archive),
                    tint = Color.White
                )
                Text(
                    text = stringResource(R.string.archive),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White
                    )
                )
            }
        }

        val offsetX = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .background(Color.White)
                .pointerInput(actionWidthPx) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                if (offsetX.value < -(actionWidthPx / 2)) {
                                    offsetX.animateTo(
                                        targetValue = -actionWidthPx,
                                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                    )
                                } else {
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                    )
                                }
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset =
                                (offsetX.value + dragAmount).coerceIn(-actionWidthPx, 0f)
                            scope.launch {
                                offsetX.snapTo(newOffset)
                            }
                        }
                    )
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    modifier = Modifier.padding(end = 16.dp),
                    visible = isEditSelected,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    RadioButton(
                        modifier = Modifier
                            .size(21.dp),
                        onClick = selectUser,
                        selected = isSelected,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.secondary,
                            unselectedColor = MaterialTheme.colorScheme.gray3
                        )
                    )
                }
                WhatsAppAsyncImage(
                    imageUrl = user.photoUrl,
                    contentDescription = stringResource(R.string.user_profile),
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = user.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier,
                            text = user.timeStamp.formatTimestamp(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Text(
                        modifier = Modifier,
                        text = user.message,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                AnimatedVisibility(
                    visible = !isEditSelected,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    Icon(
                        imageVector = WhatsappIcons.ArrowRight,
                        contentDescription = stringResource(R.string.open_chat)
                    )
                }
            }
            WhatsAppHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}