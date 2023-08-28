package api

import constant.*
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Flow
import kotlin.collections.Map

@ExtendWith(MockitoExtension::class)
class ApiClientTest {

    @Mock
    private lateinit var mockClient: HttpClient

    @Mock
    private lateinit var mockResponse: HttpResponse<String>

    @Captor
    private lateinit var captor: ArgumentCaptor<HttpRequest>

    private lateinit var apiClient: ApiClient

    @BeforeEach
    fun setup() {
        val httpRequest = HttpRequest(mockClient)
        apiClient = ApiClient(httpRequest)

        `when`(mockResponse.statusCode()).thenReturn(200)
        `when`(mockResponse.body()).thenReturn("{\"ok\":true")

        `when`(mockClient.send(any(), eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse)
    }

    /**
     * Tests for the 'sendToChannel'.
     * @see ApiClient.sendToChannel
     */
    @Test
    fun `test 'sendToChannel'`() {
        val expectedRequestBody = mapOf(
            KEY_CHAT_ID to CHANNEL_ID_USED_IN_REQUEST_BODY,
            KEY_TEXT to "Test data",
            KEY_PARSE_MODE to MARKDOWN,
            KEY_DISABLE_WEB_PAGE_PREVIEW to "true"
        )

        apiClient.sendToChannel("Test data", token)

        val capturedRequest = verifyAndCaptureHttpResponse()

        assertTelegramApiRequest(expectedRequestBody, capturedRequest, SEND_MESSAGE_URL)
    }

    /**
     * Tests for the 'sendPhotoWithCaption'.
     * @see ApiClient.sendPhotoWithCaption
     */
    @Test
    fun `test 'sendPhotoWithCaption'`() {
        val expectedRequestBody = mapOf(
            KEY_CHAT_ID to CHANNEL_ID_USED_IN_REQUEST_BODY,
            KEY_CAPTION to "Test data",
            KEY_PARSE_MODE to MARKDOWN,
            KEY_PHOTO to "https://test.com/image.jpg"
        )

        apiClient.sendPhotoWithCaption("https://test.com/image.jpg", "Test data", token)

        val capturedRequest = verifyAndCaptureHttpResponse()

        assertTelegramApiRequest(expectedRequestBody, capturedRequest, SEND_PHOTO_URL)
    }

    /**
     * Tests for the 'getTrendsRepo'.
     * @see ApiClient.getTrendsRepo
     */
    @Test
    fun `test 'getTrendsRepo'`() {
        apiClient.getTrendsRepo()

        val capturedRequest = verifyAndCaptureHttpResponse()

        assertGitterApiRequest(capturedRequest)
    }

    @AfterEach
    fun finish() {
        assertEquals(200, mockResponse.statusCode())
        assertEquals("{\"ok\":true", mockResponse.body())
    }

    private fun verifyAndCaptureHttpResponse(): HttpRequest {
        verify(mockClient).send(captor.capture(), eq(HttpResponse.BodyHandlers.ofString()))
        return captor.value
    }

    private fun assertTelegramApiRequest(
        expectedRequestBody: Map<String, String>,
        capturedRequest: HttpRequest,
        url: String
    ) {
        assertEquals(expectedRequestBody, capturedRequest.extractActualRequestBody())
        assertEquals("${TELEGRAM_API_BASE_URL}bot$token/$url", capturedRequest.uri().toString())
        assertEquals(POST_REQUEST, capturedRequest.method())
        assertEquals(JSON_CONTENT_TYPE, capturedRequest.headers().firstValue(ACCEPT_HEADER).orElse(null))
        assertEquals(JSON_CONTENT_TYPE, capturedRequest.headers().firstValue(CONTENT_TYPE).orElse(null))
    }

    private fun assertGitterApiRequest(capturedRequest: HttpRequest) {
        assertEquals(GITTER_API_BASE_URL, capturedRequest.uri().toString())
        assertEquals(GET_REQUEST, capturedRequest.method())
        assertEquals(JSON_CONTENT_TYPE, capturedRequest.headers().firstValue(ACCEPT_HEADER).orElse(null))
    }

    private fun HttpRequest.extractActualRequestBody(): Map<String, Any> {
        val body = this.bodyPublisher().get()
        val flowSubscriber: FlowSubscriber<ByteBuffer> = FlowSubscriber()
        body.subscribe(flowSubscriber)
        val actual = String(flowSubscriber.getBodyItems()[0].array())
        return JSONObject(actual).toMap()
    }

    companion object {
        private const val token = "1234"
    }

}

/*
    A comment for me!
    For checking request body every request, I created this class. Visit this link:
    https://stackoverflow.com/questions/59342963/how-to-test-java-net-http-java-11-requests-bodypublisher
 */
private class FlowSubscriber<T> : Flow.Subscriber<T> {
    private val latch = CountDownLatch(1)
    private val bodyItems: MutableList<T> = ArrayList()

    fun getBodyItems(): List<T> {
        try {
            latch.await()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        return bodyItems
    }

    override fun onSubscribe(subscription: Flow.Subscription) {
        subscription.request(Long.MAX_VALUE)
    }

    override fun onNext(item: T) {
        bodyItems.add(item)
    }

    override fun onError(throwable: Throwable) {
        latch.countDown()
    }

    override fun onComplete() {
        latch.countDown()
    }
}