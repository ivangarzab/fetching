package com.ivangarzab.tracker.managers

import com.ivangarzab.tracker.models.ComposableNode
import com.ivangarzab.tracker.models.HierarchySnapshot
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ComplianceCollector {
    private val currentHierarchy = mutableListOf<ComposableNode>()
    private val nodeStack = mutableListOf<ComposableNode>()
    private var nodeCounter = 0L

    fun enterComposable(name: String, parentId: String?, data: Map<String, String> = emptyMap()): String {
        val nodeId = "node_${++nodeCounter}"
        val node = ComposableNode(
            id = nodeId,
            name = name,
            timestamp = System.currentTimeMillis(),
            data = data
        )

        if (parentId == null) {
            // Root level node
            currentHierarchy.add(node)
        } else {
            // Find parent and add as child
            findNodeById(parentId)?.children?.add(node)
        }

        return nodeId
    }

    private fun findNodeById(id: String): ComposableNode? {
        fun searchNode(node: ComposableNode): ComposableNode? {
            if (node.id == id) return node
            for (child in node.children) {
                searchNode(child)?.let { return it }
            }
            return null
        }

        for (rootNode in currentHierarchy) {
            searchNode(rootNode)?.let { return it }
        }
        return null
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