package util

import com.google.gson.Gson
import constant.*
import io.quickchart.QuickChart

/**
 * Creates a web link to an image that displays a bar chart showcasing the usage of
 * different programming languages based on their counts in the provided language map.
 *
 * @receiver The map of languages and their usage counts.
 * @return The URL to the generated chart image.
 */
fun Map<String, Int>.generateImageUrl(): String {
    val labelsChart = this.keys.toList()
    val valuesChart = this.values.toList()
    val colorsChart = generateColorsForChart(this.size)
    val chart = QuickChart()
    chart.width = IMAGE_WIDTH
    chart.height = IMAGE_HEIGHT
    val config = mapOf(
        "type" to "bar",
        "data" to mapOf(
            "labels" to labelsChart,
            "datasets" to listOf(
                mapOf(
                    "label" to "languages",
                    "data" to valuesChart,
                    "backgroundColor" to colorsChart
                )
            )
        )
    )
    chart.config = Gson().toJson(config)
    return chart.url
}

/**
 * Generate Caption for image
 *
 * Generates a caption describing the most commonly used languages in the trending repositories
 * along with corresponding emojis.The caption is formatted with a date,
 * language names, and an identifier for a channel.
 * @receiver The map of languages and their usage counts.
 * @return The generated caption as a string.
 */
fun Map<String, Int>.generateCaption(): String {
    val emojis = generateEmojisForCaption(this.size)
    val caption = buildString {
        appendLine("*The most commonly used languages in the trending repositories on ${today()} are:*\n")
        keys.forEachIndexed { index, language ->
            appendLine("${emojis[index % emojis.size]} $language")
        }
        append("\n"+CHANNEL_ID_USED_IN_MESSAGE)
    }
    return caption
}

/**
 * Generates a list of emojis for caption formatting based on the provided size.
 *
 * @param size The size of the list.
 * @return The list of emojis.
 */
private fun generateEmojisForCaption(size: Int): ArrayList<String> {
    val circles = listOf("🟢", "🔴", "🟡", "🟠", "🔵", "🟣", "🟤")

    val generateEmojis = ArrayList<String>()

    for (i in 0..size) {
        generateEmojis.add(circles[i % circles.size])
    }
    return generateEmojis
}

/**
 * Generates a list of colors for chart bars based on the provided size.
 *
 * @param size The size of the list.
 * @return The list of colors.
 */
private fun generateColorsForChart(size: Int): ArrayList<String> {
    val colors = listOf(
        "#00FF00",
        "#FF0000",
        "#FFFF00",
        "#FFA500",
        "#0000FF",
        "#800080",
        "#964B00",
    )

    val generatedColors = ArrayList<String>()

    for (i in 0..size) {
        generatedColors.add(colors[i % colors.size])
    }
    return generatedColors
}
/*
A comment for me!
The functions `generateImageUrl` and `generateCaption` do not require
checking for an empty map since this validation is already performed
within the `getSortedLanguageMap` function.
 */