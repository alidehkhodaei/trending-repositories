package util

import constant.CHANNEL_ID_USED_IN_MESSAGE
import model.Repository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class MessageUtilsTest {

    /**
     * Tests for the 'generateMessageAndCheckLength'.
     *
     * @see List.generateMessageAndCheckLength
     */

    private val repository =
        Repository(80, "Ali", "repo", "This is description", "Kotlin", 190, "https://github.com/Ali/repo")

    private val title = "🟩 *Today:* ${today()}"

    private val content = """
                📋 *Name:* [repo](https://github.com/Ali/repo)
                📝 *Description:* This is description
                👤 *Author:* Ali
                🌐 *Language:* Kotlin
                ⭐ *Stars:* 190
                🍴 *Forks:* 80     
                """.trimIndent().trim()


    @Test
    fun `test generateMessageAndCheckLength with empty list`() {
        val repositoryList = emptyList<Repository>()
        assertThrows<IllegalArgumentException> { repositoryList.generateMessageAndCheckLength() }
    }

    @Test
    fun `test generateMessageAndCheckLength with single repository that fits`() {
        val repositoryList = listOf(repository)
        val actualString = repositoryList.generateMessageAndCheckLength()
        val expectedString = "$title\n\n$content\n\n$CHANNEL_ID_USED_IN_MESSAGE"
        assertEquals(expectedString, actualString)
    }

    @Test
    fun `test generateMessageAndCheckLength with single repository that needs truncation`() {

        // Define the size of the repository list before and after truncation
        val repositorySize = 40
        val expectedSizeAfterCheckLength = 25

        // Generate a list of repositories
        val repositoryList = ArrayList<Repository>()
        repeat(repositorySize + 1) {
            repositoryList.add(repository)
        }

        // Generate the actual result using the repository list
        val actualResult = repositoryList.generateMessageAndCheckLength()

        // Build the expected result with title, content, and channel ID
        val expectedResult = buildString {
            append("$title\n\n")
            repeat(expectedSizeAfterCheckLength + 1) {
                append("$content\n\n")
            }
            append(CHANNEL_ID_USED_IN_MESSAGE)
        }

        assertEquals(expectedResult, actualResult)
    }

}