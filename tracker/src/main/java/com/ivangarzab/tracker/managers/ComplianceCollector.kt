package com.ivangarzab.tracker.managers

import com.ivangarzab.tracker.models.ComposableNode
import com.ivangarzab.tracker.models.FrameInfo
import com.ivangarzab.tracker.models.HierarchySnapshot
import com.ivangarzab.tracker.models.LayerInfo
import com.ivangarzab.tracker.models.NodeOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ComplianceCollector {
    private val currentHierarchy = mutableListOf<ComposableNode>()
    private val nodeStack = mutableListOf<ComposableNode>()
    private var nodeCounter = 0L

    fun enterComposable(
        className: String,
        parentId: String?,
        frame: FrameInfo,
        options: NodeOptions
    ): String {
        val nodeId = "node_${++nodeCounter}"
        val node = ComposableNode(
            id = nodeId,
            options = options.copy(frame = frame),
            className = className
        )

        if (parentId == null) {
            currentHierarchy.add(node)
        } else {
            findNodeById(parentId)?.childrens?.add(node)
        }

        return nodeId
    }

    private fun findNodeById(id: String): ComposableNode? {
        fun searchNode(node: ComposableNode): ComposableNode? {
            if (node.id == id) return node
            for (child in node.childrens) {
                searchNode(child)?.let { return it }
            }
            return null
        }

        for (rootNode in currentHierarchy) {
            searchNode(rootNode)?.let { return it }
        }
        return null
    }

    fun updateNodeFrame(nodeId: String, frame: FrameInfo) {
        findNodeById(nodeId)?.let { node ->
            node.options = node.options.copy(frame = frame)
        }
    }

    fun updateNodeText(nodeId: String, text: String) {
        findNodeById(nodeId)?.let { node ->
            node.options = node.options.copy(text = text)
        }
    }

    fun updateNodeLayer(nodeId: String, layer: LayerInfo) {
        findNodeById(nodeId)?.let { node ->
            node.options = node.options.copy(layer = layer)
        }
    }

    fun exitComposable(nodeId: String) {
        if (nodeStack.isNotEmpty() && nodeStack.last().id == nodeId) {
            nodeStack.removeLastOrNull()
        } else {
            println("WARNING: Exit mismatch. Expected ${nodeStack.lastOrNull()?.id}, got $nodeId")
        }
    }

    fun generateJSON(): String {
        val snapshot = HierarchySnapshot(
            timestamp = System.currentTimeMillis(),
            rootNodes = currentHierarchy.toList()
        )
        return Json { prettyPrint = true }.encodeToString(snapshot)
    }

    fun reset() {
        currentHierarchy.clear()
        nodeStack.clear()
        nodeCounter = 0L
    }
}