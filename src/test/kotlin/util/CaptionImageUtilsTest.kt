package util

import constant.CHANNEL_ID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CaptionImageUtilsTest {

    /**
     * Tests for the 'generateImageUrl'.
     *
     * @see Map.generateImageUrl
     */
    @Test
    fun `test 'generateImageUrl'`() {
        val languagesMap = mapOf("Java" to 2, "Python" to 1)
        assertTrue(languagesMap.generateImageUrl().isNotEmpty())
    }

    /**
     * Tests for the 'generateCaption'.
     *
     * @see Map.generateCaption
     */
    @Test
    fun `test 'generateCaption'`() {
        val languageMap = mapOf(
            "Kotlin" to 10,
            "Java" to 8,
            "Python" to 5
        )

        val expectedCaption = """
            |<b>The most commonly used languages in the trending repositories on ${today()} are:</b>
            |
            |🟢 Kotlin
            |🔴 Java
            |🟡 Python
            |
            |$CHANNEL_ID
        """.trimMargin()

        val actualCaption = languageMap.generateCaption()

        assertEquals(expectedCaption, actualCaption)
    }

}