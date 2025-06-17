package com.ivangarzab.tracker.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import com.ivangarzab.tracker.managers.ComplianceCollector
import com.ivangarzab.tracker.models.FrameInfo
import com.ivangarzab.tracker.models.LayerInfo
import com.ivangarzab.tracker.models.NodeOptions

val LocalParentId = compositionLocalOf<String?> { null }

@Composable
fun TrackComposable(
    className: String,
    options: NodeOptions? = null,
    content: @Composable () -> Unit
) {
    val parentId = LocalParentId.current

    val defaultOptions = NodeOptions(
        frame = FrameInfo(0, 0, 0, 0),
        layer = LayerInfo(),
        isSelected = false,
        isHidden = false
    )

    val finalOptions = options ?: defaultOptions

    val nodeId = remember {
        ComplianceCollector.enterComposable(className, parentId, FrameInfo(0, 0, 0, 0), finalOptions)
    }

    CompositionLocalProvider(LocalParentId provides nodeId) {
        Box(modifier = Modifier.onGloballyPositioned { coordinates ->
            val realFrame = FrameInfo(
                top = coordinates.positionInRoot().y.toInt(),
                left = coordinates.positionInRoot().x.toInt(),
                width = coordinates.size.width,
                height = coordinates.size.height
            )
            ComplianceCollector.updateNodeFrame(nodeId, realFrame)
        }) {
            content()
        }
    }
}