package api

import com.google.gson.Gson
import constant.ACCEPT_HEADER
import constant.CONTENT_TYPE
import constant.JSON_CONTENT_TYPE
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * A class for making HTTP requests using the HttpClient provided by the Java standard library.
 *
 * @property client The HttpClient instance used to send HTTP requests.
 */
class HttpRequest(private val client: HttpClient) {

    /**
     * Performs a GET HTTP request.
     *
     * @param url The specific API endpoint to call.
     * @return The HTTP response containing the API data.
     */
    fun performGetHttpRequest(url: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder(URI.create(url))
            .GET()
            .header(ACCEPT_HEADER, JSON_CONTENT_TYPE)
            .build()
        return executeRequest(request)
    }

    /**
     * Performs a POST HTTP request.
     *
     * @param url The specific API endpoint to call.
     * @param requestBody The data to be sent in the request body.
     * @return The HTTP response containing the API data.
     */
    fun performPostHttpRequest(url: String, requestBody: Map<String, String>): HttpResponse<String> {
        val jsonBody = Gson().toJson(requestBody)
        val request = HttpRequest.newBuilder(URI.create(url))
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .header(ACCEPT_HEADER, JSON_CONTENT_TYPE)
            .header(CONTENT_TYPE, JSON_CONTENT_TYPE)
            .build()
        return executeRequest(request)
    }

    /**
     * Executes an HTTP request and returns the response.
     *
     * @param request The HTTP request to be executed.
     * @return The HTTP response containing the API data.
     */
    private fun executeRequest(request: HttpRequest) = client.send(request, HttpResponse.BodyHandlers.ofString())

}