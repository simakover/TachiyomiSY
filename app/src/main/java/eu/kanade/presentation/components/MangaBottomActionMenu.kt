package eu.kanade.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.SwapCalls
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.kanade.presentation.util.calculateWindowWidthSizeClass
import eu.kanade.tachiyomi.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun MangaBottomActionMenu(
    visible: Boolean,
    modifier: Modifier = Modifier,
    onBookmarkClicked: (() -> Unit)? = null,
    onRemoveBookmarkClicked: (() -> Unit)? = null,
    onMarkAsReadClicked: (() -> Unit)? = null,
    onMarkAsUnreadClicked: (() -> Unit)? = null,
    onMarkPreviousAsReadClicked: (() -> Unit)? = null,
    onDownloadClicked: (() -> Unit)? = null,
    onDeleteClicked: (() -> Unit)? = null,
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(expandFrom = Alignment.Bottom),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom),
    ) {
        val scope = rememberCoroutineScope()
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
            tonalElevation = 3.dp,
        ) {
            val haptic = LocalHapticFeedback.current
            val confirm = remember { mutableStateListOf(false, false, false, false, false, false, false) }
            var resetJob: Job? = remember { null }
            val onLongClickItem: (Int) -> Unit = { toConfirmIndex ->
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                (0 until 7).forEach { i -> confirm[i] = i == toConfirmIndex }
                resetJob?.cancel()
                resetJob = scope.launch {
                    delay(1000)
                    if (isActive) confirm[toConfirmIndex] = false
                }
            }
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
            ) {
                if (onBookmarkClicked != null) {
                    Button(
                        title = stringResource(R.string.action_bookmark),
                        icon = Icons.Default.BookmarkAdd,
                        toConfirm = confirm[0],
                        onLongClick = { onLongClickItem(0) },
                        onClick = onBookmarkClicked,
                    )
                }
                if (onRemoveBookmarkClicked != null) {
                    Button(
                        title = stringResource(R.string.action_remove_bookmark),
                        icon = Icons.Default.BookmarkRemove,
                        toConfirm = confirm[1],
                        onLongClick = { onLongClickItem(1) },
                        onClick = onRemoveBookmarkClicked,
                    )
                }
                if (onMarkAsReadClicked != null) {
                    Button(
                        title = stringResource(R.string.action_mark_as_read),
                        icon = Icons.Default.DoneAll,
                        toConfirm = confirm[2],
                        onLongClick = { onLongClickItem(2) },
                        onClick = onMarkAsReadClicked,
                    )
                }
                if (onMarkAsUnreadClicked != null) {
                    Button(
                        title = stringResource(R.string.action_mark_as_unread),
                        icon = Icons.Default.RemoveDone,
                        toConfirm = confirm[3],
                        onLongClick = { onLongClickItem(3) },
                        onClick = onMarkAsUnreadClicked,
                    )
                }
                if (onMarkPreviousAsReadClicked != null) {
                    Button(
                        title = stringResource(R.string.action_mark_previous_as_read),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_done_prev_24dp),
                        toConfirm = confirm[4],
                        onLongClick = { onLongClickItem(4) },
                        onClick = onMarkPreviousAsReadClicked,
                    )
                }
                if (onDownloadClicked != null) {
                    Button(
                        title = stringResource(R.string.action_download),
                        icon = Icons.Outlined.Download,
                        toConfirm = confirm[5],
                        onLongClick = { onLongClickItem(5) },
                        onClick = onDownloadClicked,
                    )
                }
                if (onDeleteClicked != null) {
                    Button(
                        title = stringResource(R.string.action_delete),
                        icon = Icons.Outlined.Delete,
                        toConfirm = confirm[6],
                        onLongClick = { onLongClickItem(6) },
                        onClick = onDeleteClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.Button(
    title: String,
    icon: ImageVector,
    toConfirm: Boolean,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
) {
    val animatedWeight by animateFloatAsState(if (toConfirm) 2f else 1f)
    Column(
        modifier = Modifier
            .size(48.dp)
            .weight(animatedWeight)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onLongClick = onLongClick,
                onClick = onClick,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
        )
        AnimatedVisibility(
            visible = toConfirm,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        ) {
            Text(
                text = title,
                overflow = TextOverflow.Visible,
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
fun LibraryBottomActionMenu(
    visible: Boolean,
    modifier: Modifier = Modifier,
    onChangeCategoryClicked: (() -> Unit)?,
    onMarkAsReadClicked: (() -> Unit)?,
    onMarkAsUnreadClicked: (() -> Unit)?,
    onDownloadClicked: (() -> Unit)?,
    onDeleteClicked: (() -> Unit)?,
    // SY -->
    onClickCleanTitles: (() -> Unit)?,
    onClickMigrate: (() -> Unit)?,
    onClickAddToMangaDex: (() -> Unit)?,
    onClickCache: (() -> Unit)?,
    // SY <--
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(expandFrom = Alignment.Bottom),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom),
    ) {
        val scope = rememberCoroutineScope()
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
            tonalElevation = 3.dp,
        ) {
            val haptic = LocalHapticFeedback.current
            val confirm = remember { mutableStateListOf(false, false, false, false, false /* SY --> */, false /* SY <-- */) }
            var resetJob: Job? = remember { null }
            val onLongClickItem: (Int) -> Unit = { toConfirmIndex ->
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                (0 until 5).forEach { i -> confirm[i] = i == toConfirmIndex }
                resetJob?.cancel()
                resetJob = scope.launch {
                    delay(1000)
                    if (isActive) confirm[toConfirmIndex] = false
                }
            }
            // SY -->
            val showOverflow = onClickCleanTitles != null || onClickAddToMangaDex != null || onClickCache != null
            val moveMarkPrev = onDeleteClicked != null && calculateWindowWidthSizeClass() == WindowWidthSizeClass.Compact
            var overFlowOpen by remember { mutableStateOf(false) }
            // SY <--
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
            ) {
                if (onChangeCategoryClicked != null) {
                    Button(
                        title = stringResource(R.string.action_move_category),
                        icon = Icons.Outlined.Label,
                        toConfirm = confirm[0],
                        onLongClick = { onLongClickItem(0) },
                        onClick = onChangeCategoryClicked,
                    )
                }
                if (onDownloadClicked != null) {
                    Button(
                        title = stringResource(R.string.action_download),
                        icon = Icons.Outlined.Download,
                        toConfirm = confirm[3],
                        onLongClick = { onLongClickItem(3) },
                        onClick = onDownloadClicked,
                    )
                }
                if (onDeleteClicked != null) {
                    Button(
                        title = stringResource(R.string.action_delete),
                        icon = Icons.Outlined.Delete,
                        toConfirm = confirm[4],
                        onLongClick = { onLongClickItem(4) },
                        onClick = onDeleteClicked,
                    )
                }
                // SY -->
                if (onMarkAsReadClicked != null) {
                    Button(
                        title = stringResource(R.string.action_mark_as_read),
                        icon = Icons.Default.DoneAll,
                        toConfirm = confirm[1],
                        onLongClick = { onLongClickItem(1) },
                        onClick = onMarkAsReadClicked,
                    )
                }
                if (showOverflow) {
                    if (!moveMarkPrev && onMarkAsUnreadClicked != null) {
                        Button(
                            title = stringResource(R.string.action_mark_as_unread),
                            icon = Icons.Default.RemoveDone,
                            toConfirm = confirm[2],
                            onLongClick = { onLongClickItem(2) },
                            onClick = onMarkAsUnreadClicked,
                        )
                    }
                    Button(
                        title = stringResource(R.string.label_more),
                        icon = Icons.Outlined.MoreVert,
                        toConfirm = confirm[5],
                        onLongClick = { onLongClickItem(5) },
                        onClick = { overFlowOpen = true },
                    )
                    DropdownMenu(
                        expanded = overFlowOpen,
                        onDismissRequest = { overFlowOpen = false },
                    ) {
                        if (onMarkAsUnreadClicked != null && moveMarkPrev) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.action_mark_as_unread)) },
                                onClick = onMarkAsUnreadClicked,
                            )
                        }
                        if (onClickCleanTitles != null) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.action_clean_titles)) },
                                onClick = onClickCleanTitles,
                            )
                        }
                        if (onClickMigrate != null) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.migrate)) },
                                onClick = onClickMigrate,
                            )
                        }
                        if (onClickAddToMangaDex != null) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.mangadex_add_to_follows)) },
                                onClick = onClickAddToMangaDex,
                            )
                        }
                        if (onClickCache != null) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.cache_selected)) },
                                onClick = onClickCache,
                            )
                        }
                    }
                } else {
                    if (onMarkAsUnreadClicked != null) {
                        Button(
                            title = stringResource(R.string.action_mark_as_unread),
                            icon = Icons.Default.RemoveDone,
                            toConfirm = confirm[2],
                            onLongClick = { onLongClickItem(2) },
                            onClick = onMarkAsUnreadClicked,
                        )
                    }
                    if (onClickMigrate != null) {
                        Button(
                            title = stringResource(R.string.migrate),
                            icon = Icons.Outlined.SwapCalls,
                            toConfirm = confirm[5],
                            onLongClick = { onLongClickItem(5) },
                            onClick = onClickMigrate,
                        )
                    }
                }
                // SY <--
            }
        }
    }
}
