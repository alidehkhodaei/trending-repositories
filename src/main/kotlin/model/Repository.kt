package model

import constant.DOES_NOT_EXIST_MESSAGE
import util.truncateAndEmptyOrNull

/**
 * Repository.
 *
 * This data class represents a GitHub repository.
 * It encapsulates various properties such as forks,
 * author, name, description, language, stars, and URL.
 *
 * @property forks
 * @property author
 * @property name
 * @property description
 * @property language
 * @property stars
 * @property url
 * @constructor Create [Repository]
 */
data class Repository(
    val forks: Int,
    val author: String,
    val name: String,
    val description: String?,
    val language: String?,
    val stars: Int,
    val url: String,
) {
    /**
     * Generates a formatted string representation of the repository's details.
     * Includes name, description, author, language, stars, and forks.
     *
     * @return Formatted string representation.
     */
    override fun toString(): String {
        return """
                  📋 *Name:* [$name]($url)
                  📝 *Description:* ${description.truncateAndEmptyOrNull()}
                  👤 *Author:* $author
                  🌐 *Language:* ${language ?: DOES_NOT_EXIST_MESSAGE}
                  ⭐ *Stars:* $stars
                  🍴 *Forks:* $forks                                         
              """.trimIndent().trim()
    }
}
