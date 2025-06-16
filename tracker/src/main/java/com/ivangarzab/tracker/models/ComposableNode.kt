package com.ivangarzab.tracker.models

import kotlinx.serialization.Serializable

@Serializable
data class ComposableNode(
    val id: String,
    val name: String,
    val timestamp: Long,
    val data: Map<String, String> = emptyMap(),
    val children: MutableList<ComposableNode> = mutableListOf()
)