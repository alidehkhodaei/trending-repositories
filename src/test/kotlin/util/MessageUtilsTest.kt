package util

import constant.CHANNEL_ID
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

    @Test
    fun `test generateMessageAndCheckLength with empty list`() {
        val repositoryList = emptyList<Repository>()
        assertThrows<IllegalArgumentException> { repositoryList.generateMessageAndCheckLength() }
    }

    @Test
    fun `test generateMessageAndCheckLength with single repository that fits`() {
        val repositoryList = listOf(repository)
        val actualString = repositoryList.generateMessageAndCheckLength()
        val expectedString = "$title\n\n$content\n\n$CHANNEL_ID"
        assertEquals(expectedString, actualString)
    }

    @Test
    fun `test generateMessageAndCheckLength with some repository that needs truncation`() {

        // Define the size of the repository list before and after truncation
        val repositorySize = 41
        val expectedSizeAfterCheckLength = 20

        // Generate a list of repositories
        val repositoryList = ArrayList<Repository>()
        repeat(repositorySize) {
            repositoryList.add(repository)
        }

        // Generate the actual result using the repository list
        val actualResult = repositoryList.generateMessageAndCheckLength()

        // Build the expected result with title, content, and channel ID
        val expectedResult = buildString {
            append("$title\n\n")
            repeat(expectedSizeAfterCheckLength) {
                append("$content\n\n")
            }
            append(CHANNEL_ID)
        }

        assertEquals(expectedResult, actualResult)
    }

    companion object {

        private val repository =
            Repository(80, "Ali", "repo", "This is description", "Kotlin", 190, "https://github.com/Ali/repo")

        private val title = "🟩 <b>Today:</b> ${today()}"

        private val content="""
                  📋 <b>Name:<a href="https://github.com/Ali/repo">repo</a></b>
                  📝 <b>Description:</b> This is description
                  👤 <b>Author:</b> Ali
                  🌐 <b>Language:</b> Kotlin
                  ⭐ <b>Stars:</b> 190
                  🍴 <b>Forks:</b> 80
        """.trimIndent()

    }

}