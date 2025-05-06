package com.ivangarzab.fetching.domain.usecase

import com.ivangarzab.fetching.domain.model.Hiring
import com.ivangarzab.fetching.domain.model.HiringMatrix
import com.ivangarzab.fetching.domain.model.HiringSection
import com.ivangarzab.fetching.domain.repository.HiringRepository

/**
 * The purpose of this UseCase is to fetch the hiring data from the repository, clean it up,
 * and return it in a sorted and grouped manner.
 */
class GetHiringDataUseCase(
    private val repository: HiringRepository
) {
    suspend operator fun invoke(): HiringMatrix? {
        return repository.getHiringData().fold(
            onSuccess = { hiringData: List<Hiring> ->
                getSortedMap(cleanUpData(hiringData))
            },
            onFailure = {
                null
            }
        )
    }

    /**
     * Clean up the data by removing any entries with blank names and sorting the data.
     */
    private fun cleanUpData(data: List<Hiring>): List<Hiring> {
        return sortData(pruneData(data))
    }

    /**
     * Get rid of all data that has blank names.
     */
    private fun pruneData(data: List<Hiring>): List<Hiring> {
        return data.filter { it.name.isNotBlank() }
    }

    /**
     * Sort the data by ListId and then by the number in the name.
     */
    private fun sortData(data: List<Hiring>): List<Hiring> {
        return data.sortedWith(
            compareBy(
                { it.listId },
                {
                    it.name.filter { char ->
                        char.isDigit()
                    }.toIntOrNull() ?: 0
                }
            )
        )
    }

    /**
     * Return a [HiringMatrix] object containing the data grouped by ListId and sorted by name.
     *
     * NOTE: I would probably end up making a secondary UseCase class that does this last
     * manipulation step instead of jamming it in here.
     * But for the purpose of time, I will leave this functionality here for now.
     */
    private fun getSortedMap(data: List<Hiring>): HiringMatrix {
        return HiringMatrix(
            matrix = data
                .groupBy { it.listId }
                .toSortedMap()
                .map { (listId, hiringList) ->
                    HiringSection(
                        listId = listId.toString(),
                        data = hiringList
                    )
                }
        )
    }
}