package br.com.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader
import java.lang.IllegalArgumentException

class MockGithubGistService {

    fun successApi() = getApi(successDispatcher())

    fun errorApi() = getApi(errorDispatcher())

    private fun getApi(dispatcher: Dispatcher): GithubGistService {
        val mockWebServer = MockWebServer(dispatcher)
        val mockUrl = mockWebServer.url("/").toString()
        return HttpClient(mockUrl).gitHubGistService()
    }

    private fun MockWebServer(dispatcher: Dispatcher) = MockWebServer().apply {
        this.dispatcher = dispatcher
    }

    private fun successDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.path){
                "/gists" -> MockResponse().setResponseCode(200).setBody(fileReader("SingleGist.json"))
                else -> throw IllegalArgumentException("Route ${request.path} is not implemented in mockwebserver")
            }
        }
    }

    private fun errorDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.path){
                "/gists" -> MockResponse().setResponseCode(422).setBody(UNPROCESSABLE_ENTITY)
                else -> throw IllegalArgumentException("Route ${request.path} is not implemented in mockwebserver")
            }
        }
    }

    private fun fileReader(filePath: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(filePath))
        return reader.readText().also { reader.close() }
    }

    companion object{
        private const val UNPROCESSABLE_ENTITY = "Status: 422 Unprocessable Entity"
    }
}
