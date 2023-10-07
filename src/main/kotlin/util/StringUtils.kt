package util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import constant.DOES_NOT_EXIST_MESSAGE
import constant.LIMIT_SIZE_DESCRIPTION
import model.Repository

/**
 * Checks the length of the string.
 * If the string is empty or null, returns a predefined message indicating non-existence.
 * Otherwise, truncates the string to a specified limit and replace `<` symbol with `&lt;` (`<` causes an error in html parse mode, so I replace it with `&lt;`).
 *
 * @receiver The input string to be truncated or checked for emptiness.
 * @return Processed string or "DOES NOT EXIST" message.
 */
fun String?.truncateAndCheckEmptyOrNull(): String {
    return if (this.isNullOrEmpty()) {
        DOES_NOT_EXIST_MESSAGE
    } else {
        this.truncate().replace("<", "&lt;")
    }
}

/**
 * Truncates the string to a specified limit and appends "..." if needed.
 *
 * @param limit Maximum length of the truncated string.
 * @receiver The input string to be truncated.
 * @return Truncated string.
 */
private fun String.truncate(limit: Int = LIMIT_SIZE_DESCRIPTION): String {
    if (this.length <= limit) {
        return this
    }
    return this.substring(0, limit) + "..."
}

/**
 * Parses the JSON string to a list of Repository objects using Gson.
 *
 * @receiver The JSON string to be parsed.
 * @return List of Repository objects parsed from the JSON string.
 */
fun String.parseToReposList(): List<Repository> {
    val gson = Gson()
    val reposListType = object : TypeToken<List<Repository>>() {}.type
    return gson.fromJson(this, reposListType)
}
