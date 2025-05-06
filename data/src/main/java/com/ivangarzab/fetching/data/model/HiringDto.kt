package com.ivangarzab.fetching.data.model

import com.ivangarzab.fetching.domain.model.Hiring

/**
 * The Hiring data class represents a raw hiring record, has fetched from the API,
 * with an id, listId, and name fields.
 */
data class HiringDto(
    val id: Int,
    val listId: Int,
    val name: String?
) {
    /**
     * Converts the [HiringDto] to a [Hiring] domain model.
     */
    fun toDomainModel(): Hiring {
        return Hiring(
            id = id,
            listId = listId,
            name = name ?: "" // Consolidate blanks and null into blank names.
        )
    }
}
