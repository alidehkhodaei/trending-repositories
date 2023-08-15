import api.*
import util.*
import java.net.http.HttpClient

/*
Lazily initializes and holds an instance of HttpClient for making HTTP requests.
The lazy initialization strategy creates only a single instance of the HttpClient
and ensures that this instance is reused for every request.
 */

private val client: HttpClient by lazy {
    HttpClient.newHttpClient()
}

fun main(args: Array<String>) {

   // httpRequest handles HTTP requests, while apiClient manages API interactions
    val httpRequest=HttpRequest(client)
    val apiRequest=ApiClient(httpRequest)

    // Fetch and parse trending repositories
    val trendsRepoString = apiRequest.getTrendsRepo()
    val trendsRepoList = trendsRepoString.parseToReposList()

    println(trendsRepoList)

    // Generates the channel message and also checks its length
    val messageChannel = trendsRepoList.generateMessageAndCheckLength()

    // Generate sorted map of programming languages with their counts
    val sortedLanguageMap = trendsRepoList.getSortedLanguageMap()

    // Generate image URL and caption
    val photo = sortedLanguageMap.generateImageUrl()
    val caption = sortedLanguageMap.generateCaption()

    // Retrieve bot token from command-line arguments
    val token = args[0]

    // Send message to designated channel
    val channelMessageResponse = apiRequest.sendToChannel(messageChannel, token)
    println(channelMessageResponse)

    // Send image with caption
    val photoCaptionResponse = apiRequest.sendPhotoWithCaption(photo, caption, token)
    println(photoCaptionResponse)
}