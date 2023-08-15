package util

import model.Repository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SortedLanguageUtilsTest {

    /**
     * Tests for the 'getSortedLanguageMap'.
     *
     * @see List.getSortedLanguageMap
     */

    @Test
    fun `test 'getSortedLanguageMap' with mixed languages`() {
        val repositories = listOf(
            Repository(
                forks = 70,
                author = "Ali",
                name = "hello-world",
                description = null,
                language = null,
                stars = 100,
                url = "https://github.com/ali/hello-world"
            ), Repository(
                forks = 18,
                author = "Reza",
                name = "hello-world",
                description = null,
                language = "Python",
                stars = 190,
                url = "https://github.com/reza/hello-world"
            ),
            Repository(
                forks = 50,
                author = "Shabnam",
                name = "hello-world",
                description = null,
                language = "Java",
                stars = 80,
                url = "https://github.com/shabnam/hello-world"
            ),
            Repository(
                forks = 10,
                author = "Fatemeh",
                name = "hello-world",
                description = "This is description",
                language = "Java",
                stars = 8,
                url = "https://github.com/fatemeh/hello-world"
            )
        )
        val actualSortedLanguages = repositories.getSortedLanguageMap()
        val expectedSortedLanguages = mapOf("Java" to 2, "Python" to 1)
        Assertions.assertEquals(expectedSortedLanguages, actualSortedLanguages)
    }

    @Test
    fun `test 'getSortedLanguageMap' with empty list`() {
        val repositories = emptyList<Repository>()
        assertThrows<IllegalArgumentException> { repositories.getSortedLanguageMap() }
    }

}
