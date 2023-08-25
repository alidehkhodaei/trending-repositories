package api

import constant.*

/**
 * A class for interacting with various APIs, including the Gitter API and the Telegram Bot API.
 *
 * @property httpRequest The instance of `HttpRequest` used to perform HTTP requests.
 */
class ApiClient(private val httpRequest: HttpRequest) {

    /**
     * Retrieves trending repositories using the Gitter API.
     *
     * @return The response body containing the trending repositories.
     */
    fun getTrendsRepo(): String {
        val response = httpRequest.performGetHttpRequest(GITTER_API_BASE_URL)
        return response.body()
    }

    /**
     * Sends a text message to a channel using the Telegram Bot API.
     *
     * @param data The text message to send.
     * @param token The authorization token for the Telegram Bot.
     * @return The response body from the API call.
     */
    fun sendToChannel(data: String, token: String): String {
        val requestBody = mapOf(
            KEY_CHAT_ID to CHANNEL_ID_USED_IN_REQUEST_BODY,
            KEY_TEXT to data,
            KEY_PARSE_MODE to MARKDOWN,
            KEY_DISABLE_WEB_PAGE_PREVIEW to "true"
        )

        val apiUrl = String.format(
            "${TELEGRAM_API_BASE_URL}bot%s/$SEND_MESSAGE_URL",
            token
        )

        val response = httpRequest.performPostHttpRequest(apiUrl, requestBody)
        return response.body()
    }

    /**
     * Sends a photo with a caption to a channel using the Telegram Bot API.
     *
     * @param photo The URL of the photo to send.
     * @param caption The caption for the photo.
     * @param token The authorization token for the Telegram Bot.
     * @return The response body from the API call.
     */
    fun sendPhotoWithCaption(photo: String, caption: String, token: String): String {
        val requestBody = mapOf(
            KEY_CHAT_ID to CHANNEL_ID_USED_IN_REQUEST_BODY,
            KEY_CAPTION to caption,
            KEY_PARSE_MODE to MARKDOWN,
            KEY_PHOTO to photo
        )

        val apiUrl = String.format(
            "${TELEGRAM_API_BASE_URL}bot%s/$SEND_PHOTO_URL",
            token
        )

        val response = httpRequest.performPostHttpRequest(apiUrl, requestBody)
        return response.body()
    }


}