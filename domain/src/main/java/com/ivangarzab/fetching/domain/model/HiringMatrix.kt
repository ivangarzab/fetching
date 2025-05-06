package com.ivangarzab.fetching.domain.model

/**
 * The purpose of this data class is to represent a hiring matrix,
 * which is a collection of [HiringSection], as will be used by the UI layer
 */
data class HiringMatrix(
    val matrix: List<HiringSection>
)

data class HiringSection(
    val listId: String,
    val data: List<Hiring>
)
