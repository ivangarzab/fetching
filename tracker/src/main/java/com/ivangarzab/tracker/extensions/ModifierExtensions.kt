package com.ivangarzab.tracker.extensions

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import com.ivangarzab.tracker.managers.ComplianceCollector
import com.ivangarzab.tracker.models.FrameInfo
import com.ivangarzab.tracker.models.LayerInfo

enum class ComponentType {
    INPUT,      // Text fields, text areas
    BUTTON,     // Buttons, clickable actions
    SWITCH,     // Toggles, switches
    DROPDOWN,   // Dropdowns, spinners, pickers
    RADIO,      // Radio button groups
    CHECKBOX,   // Checkboxes, multi-select
    WEBVIEW,    // Embedded web content
    IMAGE,      // Images, icons
    CONTENT,    // Text, headers, labels
    CUSTOM      // Custom composables, fallback
}

fun Modifier.complianceTrack(
    label: String,
    type: ComponentType = ComponentType.CONTENT,
    value: String? = null,
    masked: Boolean = false,
    enabled: Boolean = true,
    url: String? = null,
    metadata: Map<String, String> = emptyMap()  // Changed from Any to String
): Modifier {
    return this.composed {
        val nodeId = remember {
            ComplianceCollector.createFlatElement(label, type, value, masked, enabled, url, metadata)
        }

        // Update text value when it changes (passive)
        LaunchedEffect(value) {
            if (value != null) {
                ComplianceCollector.updateNodeText(nodeId, value)
            }
        }

        // Capture layout coordinates (passive)
        this@composed.onGloballyPositioned { coordinates ->
            val realFrame = FrameInfo(
                top = coordinates.positionInRoot().y.toInt(),
                left = coordinates.positionInRoot().x.toInt(),
                width = coordinates.size.width,
                height = coordinates.size.height
            )
            ComplianceCollector.updateNodeFrame(nodeId, realFrame)
        }
    }
}