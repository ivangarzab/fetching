package com.ivangarzab.tracker.models

import kotlinx.serialization.Serializable

@Serializable
data class FrameInfo(
    val top: Int,
    val left: Int,
    val width: Int,
    val height: Int
)

@Serializable
data class LayerInfo(
    val borderColor: String = "#000000",
    val borderWidth: Float = 0f,
    val cornerRadius: Float = 0f,
    val opacity: Int = 1
)

@Serializable
data class FontInfo(
    val fontSize: Int
)

@Serializable
data class NodeOptions(
    val frame: FrameInfo,
    val layer: LayerInfo? = null,
    val isSelected: Boolean = false,
    val isHidden: Boolean = false,
    val backgroundColor: String? = null,
    // Text-specific properties
    val font: FontInfo? = null,
    val text: String? = null,
    val textColor: String? = null,
    val numberOfLines: Int? = null,
    val textAlignment: Int? = null,
    // Other properties
    val progress: Float? = null
)

@Serializable
data class ComposableNode(
    val id: String,
    var options: NodeOptions,  // Changed from 'val' to 'var'
    val childrens: MutableList<ComposableNode> = mutableListOf(),
    val className: String
)