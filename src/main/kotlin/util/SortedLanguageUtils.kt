package util

import model.Repository


/**
 * Sorts programming languages and their corresponding repetition counts.
 *
 * This function takes a list of Repository objects and generates a map that associates
 * programming languages with the number of times they appear in the list. The map is then
 * sorted based on the repetition count.
 *
 * @return A sorted map of programming languages and their repetition counts.
 * @receiver The list of repositories.
 * @throws IllegalArgumentException if the list of repositories is empty.
 */
fun List<Repository>.getSortedLanguageMap(): Map<String, Int> {
    require(this.isNotEmpty()){"List<Repository> is empty"}

    val languagesMap = HashMap<String, Int>()

    // Get name languages
    val languageNames = HashSet<String>()
    this.forEach { repository ->
        if (repository.language != null)
            languageNames.add(repository.language)
    }

    // Get count language
    languageNames.forEach { lang ->
        var count = 0
        this.forEach { repository ->
            if (lang == repository.language) {
                count++
            }
        }
        languagesMap[lang] = count
    }

    // Sort map
    return languagesMap.toList().sortedByDescending { (_, v) -> v }.toMap()

}