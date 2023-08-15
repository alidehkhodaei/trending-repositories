package util

import constant.DOES_NOT_EXIST_MESSAGE
import model.Repository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringUtilsTest {

    /**
     * Tests for the 'parseToReposList'.
     *
     * @see String.parseToReposList
     */

    @Test
    fun `test parsing single repository with all fields filled`() {
        val jsonString =
            "[{\"forks\":17,\"author\":\"Ali\",\"name\":\"repo\",\"description\":\"This is a repository\",\"language\":\"Kotlin\",\"stars\":100,\"url\":\"https://github.com/test/repo\"}]"
        val expectedList = listOf(
            Repository(
                forks = 17,
                author = "Ali",
                name = "repo",
                description = "This is a repository",
                language = "Kotlin",
                stars = 100,
                url = "https://github.com/test/repo"
            )
        )
        val actualList = jsonString.parseToReposList()
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `test parsing single repository with some fields missing`() {
        val jsonString =
            "[{\"forks\":10,\"author\":\"Ali\",\"name\":\"hello-world\",\"stars\":100,\"url\":\"https://github.com/test/hello-world\"}]"
        val expectedList = listOf(
            Repository(
                forks = 10,
                author = "Ali",
                name = "hello-world",
                description = null,
                language = null,
                stars = 100,
                url = "https://github.com/test/hello-world"
            )
        )
        val actualList = jsonString.parseToReposList()
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `test parsing single repository with empty description`() {
        val jsonString =
            "[{\"forks\":10,\"author\":\"Ali\",\"name\":\"hello-world\",\"description\":\"\",\"language\":\"Kotlin\",\"stars\":100,\"url\":\"https://github.com/test/hello-world\"}]"
        val expectedList = listOf(
            Repository(
                forks = 10,
                author = "Ali",
                name = "hello-world",
                description = "",
                language = "Kotlin",
                stars = 100,
                url = "https://github.com/test/hello-world"
            )
        )
        val actualList = jsonString.parseToReposList()
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `test parsing multiple repositories with various fields`() {
        val jsonString =
            "[{\"forks\":10,\"author\":\"Ali\",\"name\":\"hello-world\",\"description\":\"This is description\",\"language\":\"Kotlin\",\"stars\":100,\"url\":\"https://github.com/test/hello-world\"}," +
                    "{\"forks\":8,\"author\":\"Reza\",\"name\":\"program\",\"description\":\"This is description 2\",\"language\":\"Python\",\"stars\":170,\"url\":\"https://github.com/reza/program\"}]"
        val expectedList = listOf(
            Repository(
                forks = 10,
                author = "Ali",
                name = "hello-world",
                description = "This is description",
                language = "Kotlin",
                stars = 100,
                url = "https://github.com/test/hello-world"
            ), Repository(
                forks = 8,
                author = "Reza",
                name = "program",
                description = "This is description 2",
                language = "Python",
                stars = 170,
                url = "https://github.com/reza/program"
            )
        )
        val actualList = jsonString.parseToReposList()
        assertEquals(expectedList, actualList)
    }

    /**
     * Tests for the 'truncateAndEmptyOrNull'.
     *
     * @see String.truncateAndEmptyOrNull
     */

    @Test
    fun `test truncateAndEmptyOrNull with empty string`() {
        val actual = "".truncateAndEmptyOrNull()
        assertEquals(DOES_NOT_EXIST_MESSAGE, actual)
    }

    @Test
    fun `test truncateAndEmptyOrNull with null`() {
        val actual = null.truncateAndEmptyOrNull()
        assertEquals(DOES_NOT_EXIST_MESSAGE, actual)
    }

    @Test
    fun `test truncateAndEmptyOrNull with special characters in string`() {
        val input = "This is a long string with special characters*&`"
        val expected = "This is a long string with special characters"
        val actual = input.truncateAndEmptyOrNull()
        assertEquals(expected, actual)
    }

    @Test
    fun `test truncateAndEmptyOrNull with long string`() {
        val input =
            "A bot that automatically fetches trending repositories and sends them to a channel. This is test string"
        val expected =
            "A bot that automatically fetches trending repositories and sends them to a channel. This is test str..."
        val actual = input.truncateAndEmptyOrNull()
        assertEquals(expected, actual)
    }

}

/*
  A comment for me!
  When the stars and forks properties are not present in the JSON string,
  the gson.fromJson() function sets their value to 0 because they are of type Int.
  On the other hand, when a property of type String is not present in the JSON string,
  the gson.fromJson() function sets its value to null.
 */





