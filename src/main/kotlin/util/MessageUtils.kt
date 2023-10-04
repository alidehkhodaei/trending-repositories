package util

import constant.CHANNEL_ID
import constant.MAX_SIZE_MESSAGE
import model.Repository
import java.lang.StringBuilder

/**
 * Generates a message for channel and checks its length.
 * The function constructs a message containing the current date and a list of repositories.
 * If the message length exceeds the maximum allowed size, it reduces the number of repositories
 * until the message fits within the limit.
 *
 * @return The generated message as a string.
 * @receiver The list of repositories.
 * @throws IllegalArgumentException if the list of repositories is empty.
 */
fun List<Repository>.generateMessageAndCheckLength(): String {
    require(this.isNotEmpty()) { "List<Repository> is empty" }

    val title = "🟩 <b>Today:</b> ${today()}"
    var repositories = this
    while (getMessageChannel(title, repositories).length > MAX_SIZE_MESSAGE) {
        repositories = repositories.subList(0, repositories.size - 1)
    }
    return getMessageChannel(title, repositories)
}

/**
 * Constructs a message containing the provided title, list of repositories, and channel identifier.
 *
 * @param title The title for the message.
 * @param repositories The list of repositories.
 * @return The formatted message as a string.
 */
private fun getMessageChannel(title: String, repositories: List<Repository>) =
    (title + "\n" + repositories.convertListRepositoryToString() + "\n" + CHANNEL_ID)

/**
 * Converts a list of Repository objects to a formatted string representation.
 * @receiver The list of repository objects to be converted.
 * @return The formatted string representation of the list of repositories.
 */
private fun List<Repository>.convertListRepositoryToString(): String {
    val repositoriesString = StringBuilder()
    this.forEach { repository ->
        repositoriesString.append("\n" + repository.toString() + "\n")
    }
    return repositoriesString.toString()
}




