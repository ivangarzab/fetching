package com.ivangarzab.tracker.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.ivangarzab.tracker.managers.ComplianceCollector

val LocalParentId = compositionLocalOf<String?> { null }

@Composable
fun TrackComposable(
    name: String,
    data: Map<String, String> = emptyMap(),
    content: @Composable () -> Unit
) {
    val parentId = LocalParentId.current
    val nodeId = remember { ComplianceCollector.enterComposable(name, parentId, data) }

    CompositionLocalProvider(LocalParentId provides nodeId) {
        content()
    }
}