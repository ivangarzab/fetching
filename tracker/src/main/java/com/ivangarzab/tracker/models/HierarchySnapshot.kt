package com.ivangarzab.tracker.models

import kotlinx.serialization.Serializable

@Serializable
data class HierarchySnapshot(
    val timestamp: Long,
    val rootNodes: List<ComposableNode>
)