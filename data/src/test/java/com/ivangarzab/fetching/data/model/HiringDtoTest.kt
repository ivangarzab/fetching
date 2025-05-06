package com.ivangarzab.fetching.data.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * The purpose of this class is to test the [HiringDto] class.
 */
class HiringDtoTest {

    private val hiringDto = HiringDto(
        id = 1,
        listId = 1,
        name = "1"
    )

    @Test
    fun testToDomainModel() {
        val result = hiringDto.toDomainModel()
        assertEquals(hiringDto.id, result.id)
        assertEquals(hiringDto.listId, result.listId)
        assertEquals(hiringDto.name, result.name)
    }
}